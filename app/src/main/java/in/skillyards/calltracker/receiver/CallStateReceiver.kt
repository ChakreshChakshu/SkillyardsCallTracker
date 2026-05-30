package `in`.skillyards.calltracker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import `in`.skillyards.calltracker.prefs.CallPreferences
import `in`.skillyards.calltracker.service.CallUploadService
import `in`.skillyards.calltracker.util.Logger

class CallStateReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "CallStateReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        
        val prefs = CallPreferences(context)
        if (!prefs.isServiceActive) return

        Logger.d(context, TAG, "Broadcast received: $action")

        if (action == Intent.ACTION_NEW_OUTGOING_CALL) {
            val outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER) ?: ""
            if (outgoingNumber.isNotEmpty()) {
                prefs.clearCallState()
                prefs.outgoingNumber = outgoingNumber
                prefs.isCallActive = true
                prefs.callStartTime = 0L // Connection starts when transition to OFFHOOK occurs
                Logger.i(context, TAG, "Outgoing call registered: $outgoingNumber. Saved to persistent preferences.")
            }
        } else if (action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE) ?: return
            
            Logger.d(context, TAG, "TelephonyManager broadcast event: $state")

            when (state) {
                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    // Outgoing or Incoming call connected
                    // On Android 10+, ACTION_NEW_OUTGOING_CALL might not fire, so we track state here
                    if (!prefs.isCallActive) {
                        prefs.isCallActive = true
                        prefs.callStartTime = System.currentTimeMillis()
                        // Try to get number from intent, though it's often null for outgoing calls
                        val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                        if (number != null) prefs.outgoingNumber = number
                        Logger.i(context, TAG, "Call detected (Offhook). Number in intent: $number")
                    } else if (prefs.callStartTime == 0L) {
                        prefs.callStartTime = System.currentTimeMillis()
                        Logger.i(context, TAG, "Call connected (Offhook state). Set callStartTime.")
                    }
                }
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    // Call terminated
                    if (prefs.isCallActive) {
                        val callEndTime = System.currentTimeMillis()
                        val callStartTime = if (prefs.callStartTime > 0L) prefs.callStartTime else callEndTime
                        val outgoingNumber = prefs.outgoingNumber ?: "Unknown"

                        Logger.i(
                            context,
                            TAG,
                            "Call completed (Idle state). Dialed: $outgoingNumber. Spawning uploader."
                        )

                        val serviceIntent = Intent(context, CallUploadService::class.java).apply {
                            putExtra(CallUploadService.EXTRA_OUTGOING_NUMBER, outgoingNumber)
                            putExtra(CallUploadService.EXTRA_START_TIME, callStartTime)
                            putExtra(CallUploadService.EXTRA_END_TIME, callEndTime)
                            putExtra(CallUploadService.EXTRA_DURATION, ((callEndTime - callStartTime) / 1000).toInt())
                        }

                        try {
                            ContextCompat.startForegroundService(context, serviceIntent)
                        } catch (e: Exception) {
                            Logger.e(context, TAG, "Failed to start Foreground Service", e)
                        }

                        prefs.clearCallState()
                    }
                }
            }
        }
    }
}
