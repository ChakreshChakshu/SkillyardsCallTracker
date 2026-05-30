package `in`.skillyards.calltracker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import `in`.skillyards.calltracker.prefs.CallPreferences
import `in`.skillyards.calltracker.util.Logger
import `in`.skillyards.calltracker.worker.RetryUploadWorker

class BootReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "BootReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        if (action == Intent.ACTION_BOOT_COMPLETED) {
            val prefs = CallPreferences(context)
            if (prefs.isServiceActive) {
                Logger.i(context, TAG, "System boot detected. Restoring background upload schedules.")
                try {
                    // Reschedule room db retries on WorkManager
                    RetryUploadWorker.schedule(context)
                } catch (e: Exception) {
                    Logger.e(context, TAG, "Error scheduling background retry task on boot", e)
                }
            } else {
                Logger.d(context, TAG, "System boot detected, but service has not been activated yet. Skipping.")
            }
        }
    }
}
