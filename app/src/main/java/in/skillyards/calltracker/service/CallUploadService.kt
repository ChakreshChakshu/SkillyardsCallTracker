package `in`.skillyards.calltracker.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import `in`.skillyards.calltracker.R
import `in`.skillyards.calltracker.data.AppDatabase
import `in`.skillyards.calltracker.data.CallRecord
import `in`.skillyards.calltracker.data.PendingUploadEntity
import `in`.skillyards.calltracker.prefs.CallPreferences
import `in`.skillyards.calltracker.recording.RecordingFinder
import `in`.skillyards.calltracker.upload.ApiUploader
import `in`.skillyards.calltracker.util.DeviceUtils
import `in`.skillyards.calltracker.util.Logger
import `in`.skillyards.calltracker.worker.RetryUploadWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File

class CallUploadService : Service() {

    companion object {
        private const val TAG = "CallUploadService"
        private const val CHANNEL_ID = "call_tracker_channel"
        private const val NOTIFICATION_ID = 1989

        const val EXTRA_OUTGOING_NUMBER = "extra_outgoing_number"
        const val EXTRA_START_TIME = "extra_start_time"
        const val EXTRA_END_TIME = "extra_end_time"
        const val EXTRA_DURATION = "extra_duration"
    }

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var prefs: CallPreferences
    private lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        prefs = CallPreferences(this)
        db = AppDatabase.getDatabase(this)
        Logger.d(this, TAG, "Background upload service created.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.d(this, TAG, "Background upload service triggered.")

        createNotificationChannel()
        val notification = buildNotification()
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                NOTIFICATION_ID, 
                notification, 
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }

        if (intent == null) {
            Logger.w(this, TAG, "Service restarted with null intent. Halting service.")
            stopSelf()
            return START_NOT_STICKY
        }

        val intentNumber = intent.getStringExtra(EXTRA_OUTGOING_NUMBER) ?: ""
        val callStartTime = intent.getLongExtra(EXTRA_START_TIME, 0L)
        val callEndTime = intent.getLongExtra(EXTRA_END_TIME, 0L)
        val durationSeconds = intent.getIntExtra(EXTRA_DURATION, 0)

        val telecallerId = prefs.telecallerId
        if (telecallerId.isNullOrEmpty()) {
            Logger.e(this, TAG, "Abort: Telecaller ID is not configured in settings!")
            stopSelf()
            return START_NOT_STICKY
        }

        // Handle the core search, compilation, and upload asynchronously on a background Coroutine
        serviceScope.launch {
            try {
                // Wait 3 seconds for the system CallLog and file system to update
                kotlinx.coroutines.delay(3000)

                var outgoingNumber = intentNumber
                
                // If number is missing or "Unknown", try fetching from CallLog
                if (outgoingNumber.isEmpty() || outgoingNumber == "Unknown") {
                    val lastNumber = `in`.skillyards.calltracker.util.CallLogUtils.getLastOutgoingNumber(this@CallUploadService)
                    if (lastNumber != null) {
                        outgoingNumber = lastNumber
                        Logger.i(this@CallUploadService, TAG, "Recovered number from CallLog: $outgoingNumber")
                    }
                }

                if (outgoingNumber.isEmpty() || outgoingNumber == "Unknown") {
                    Logger.w(this@CallUploadService, TAG, "Missing outgoing number param. Halting service.")
                    return@launch
                }

                Logger.i(this@CallUploadService, TAG, "Starting background call processing: Target=$outgoingNumber, Duration=${durationSeconds}s")
                
                var recordingStatus = "not_found"
                var recordingFile: File? = null

                if (DeviceUtils.isGoogleDialerDefault(this@CallUploadService)) {
                    recordingStatus = "no_dialer_support"
                    Logger.w(this@CallUploadService, TAG, "Google Dialer detected. Silent call recording blocked by OS. Skipping recording.")
                } else {
                    // Start search polling for Call Recording
                    recordingFile = RecordingFinder.findCallRecording(this@CallUploadService, outgoingNumber, callEndTime)
                    if (recordingFile != null) {
                        recordingStatus = "found"
                    }
                }

                val record = CallRecord(
                    telecallerId = telecallerId,
                    toNumber = outgoingNumber,
                    callStartTime = callStartTime,
                    callEndTime = callEndTime,
                    durationSeconds = durationSeconds,
                    recordingPath = recordingFile?.absolutePath,
                    recordingStatus = recordingStatus
                )

                // Execute immediate upload request
                val success = ApiUploader.uploadCallRecord(this@CallUploadService, prefs.apiBaseUrl, record)

                if (success) {
                    Logger.i(this@CallUploadService, TAG, "Call record upload succeeded immediately.")
                    prefs.lastUploadTimestamp = System.currentTimeMillis()
                    
                    // Clean up device storage by deleting the uploaded call recording file
                    if (recordingFile != null && recordingFile.exists()) {
                        val deleted = recordingFile.delete()
                        Logger.d(this@CallUploadService, TAG, "Cleaned up uploaded recording: $deleted")
                    }
                    prefs.clearCallState()
                } else {
                    Logger.w(this@CallUploadService, TAG, "Immediate upload failed. Enqueueing to local Room DB...")
                    
                    val entity = PendingUploadEntity(
                        telecallerId = record.telecallerId,
                        toNumber = record.toNumber,
                        callStartTime = record.callStartTime,
                        callEndTime = record.callEndTime,
                        durationSeconds = record.durationSeconds,
                        recordingPath = record.recordingPath,
                        recordingStatus = record.recordingStatus
                    )
                    
                    db.pendingUploadDao().insert(entity)
                    
                    // Trigger retry WorkManager
                    RetryUploadWorker.schedule(this@CallUploadService)
                    prefs.clearCallState()
                }
            } catch (e: Exception) {
                Logger.e(this@CallUploadService, TAG, "Unexpected error in service thread loop: ${e.message}", e)
            } finally {
                Logger.d(this@CallUploadService, TAG, "Stopping foreground service context.")
                stopSelf()
            }
        }

        return START_NOT_STICKY
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("SkillYards Call Tracker")
            .setContentText("Uploading call details securely...")
            .setSmallIcon(R.drawable.logo_skillyards)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "SkillYards Background Tracker",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Silent foreground notification keeping Call Tracker active"
                setShowBadge(false)
                enableLights(false)
                enableVibration(false)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    override fun onDestroy() {
        serviceScope.cancel()
        Logger.d(this, TAG, "Service context destroyed.")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
