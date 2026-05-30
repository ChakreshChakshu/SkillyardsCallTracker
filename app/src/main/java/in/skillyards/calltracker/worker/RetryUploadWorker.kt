package `in`.skillyards.calltracker.worker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import `in`.skillyards.calltracker.data.AppDatabase
import `in`.skillyards.calltracker.data.CallRecord
import `in`.skillyards.calltracker.prefs.CallPreferences
import `in`.skillyards.calltracker.upload.ApiUploader
import `in`.skillyards.calltracker.util.Logger
import java.io.File
import java.util.concurrent.TimeUnit

class RetryUploadWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val TAG = "RetryUploadWorker"
        private const val UNIQUE_WORK_NAME = "SkillyardsRetryUploadWork"

        /**
         * Enqueues the WorkManager background task under CONNECTED network constraint.
         * Leverages a 15-minute recurring loop along with an immediate one-time pass.
         */
        fun schedule(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            // 1. Enqueue 15-minute periodic task to process accumulated items in background
            val periodicRequest = PeriodicWorkRequestBuilder<RetryUploadWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    1,
                    TimeUnit.MINUTES
                )
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                UNIQUE_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicRequest
            )

            // 2. Queue an immediate one-off execution to run when connectivity is restored
            val oneTimeRequest = OneTimeWorkRequestBuilder<RetryUploadWorker>()
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueue(oneTimeRequest)
            Logger.d(context, TAG, "WorkManager background schedule created: 15-minute Periodic + Immediate OneTime.")
        }
    }

    private val db = AppDatabase.getDatabase(applicationContext)
    private val prefs = CallPreferences(applicationContext)

    override suspend fun doWork(): Result {
        Logger.i(applicationContext, TAG, "WorkManager running retry uploads...")
        
        val pendingDao = db.pendingUploadDao()
        val pendingList = pendingDao.getActivePendingUploads()

        if (pendingList.isEmpty()) {
            Logger.d(applicationContext, TAG, "No active pending items found in SQLite queue.")
            return Result.success()
        }

        var anyFailures = false

        for (entity in pendingList) {
            Logger.i(
                applicationContext,
                TAG,
                "Retrying payload id: ${entity.id}, target: ${entity.toNumber}, attempt: ${entity.attemptCount + 1}/48"
            )

            val record = CallRecord(
                telecallerId = entity.telecallerId,
                toNumber = entity.toNumber,
                callStartTime = entity.callStartTime,
                callEndTime = entity.callEndTime,
                durationSeconds = entity.durationSeconds,
                recordingPath = entity.recordingPath,
                recordingStatus = entity.recordingStatus
            )

            // Attempt api upload
            val success = ApiUploader.uploadCallRecord(applicationContext, prefs.apiBaseUrl, record)

            if (success) {
                Logger.i(applicationContext, TAG, "Retry upload succeeded for database log id: ${entity.id}")
                
                // Clear media from local directory on success
                if (!entity.recordingPath.isNullOrEmpty()) {
                    val file = File(entity.recordingPath)
                    if (file.exists()) {
                        val deleted = file.delete()
                        Logger.d(applicationContext, TAG, "Cleaned up uploaded media: $deleted")
                    }
                }
                
                pendingDao.delete(entity)
                prefs.lastUploadTimestamp = System.currentTimeMillis()
            } else {
                val nextAttempt = entity.attemptCount + 1
                Logger.w(
                    applicationContext,
                    TAG,
                    "Retry upload failed for database log id: ${entity.id}. Marking attempt $nextAttempt/48"
                )
                
                val updatedEntity = entity.copy(
                    attemptCount = nextAttempt,
                    lastAttemptTime = System.currentTimeMillis()
                )
                pendingDao.update(updatedEntity)
                anyFailures = true
            }
        }

        return if (anyFailures) {
            Result.retry()
        } else {
            Result.success()
        }
    }
}
