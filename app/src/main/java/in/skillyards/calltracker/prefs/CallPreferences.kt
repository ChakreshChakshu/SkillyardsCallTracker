package `in`.skillyards.calltracker.prefs

import android.content.Context
import android.content.SharedPreferences

class CallPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("call_tracker_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TELECALLER_ID = "telecaller_id"
        private const val KEY_API_BASE_URL = "api_base_url"
        private const val KEY_OUTGOING_NUMBER = "outgoing_number"
        private const val KEY_CALL_START_TIME = "call_start_time"
        private const val KEY_CALL_END_TIME = "call_end_time"
        private const val KEY_DURATION_SECONDS = "duration_seconds"
        private const val KEY_IS_CALL_ACTIVE = "is_call_active"
        private const val KEY_LAST_UPLOAD_TIMESTAMP = "last_upload_timestamp"
        private const val KEY_IS_SERVICE_ACTIVE = "is_service_active"
    }

    var telecallerId: String?
        get() = prefs.getString(KEY_TELECALLER_ID, null)
        set(value) = prefs.edit().putString(KEY_TELECALLER_ID, value).apply()

    var apiBaseUrl: String
        get() = prefs.getString(KEY_API_BASE_URL, "https://blowiest-tyrannicidal-cordie.ngrok-free.dev") ?: "https://blowiest-tyrannicidal-cordie.ngrok-free.dev"
        set(value) = prefs.edit().putString(KEY_API_BASE_URL, value).apply()

    var outgoingNumber: String?
        get() = prefs.getString(KEY_OUTGOING_NUMBER, null)
        set(value) = prefs.edit().putString(KEY_OUTGOING_NUMBER, value).apply()

    var callStartTime: Long
        get() = prefs.getLong(KEY_CALL_START_TIME, 0L)
        set(value) = prefs.edit().putLong(KEY_CALL_START_TIME, value).apply()

    var callEndTime: Long
        get() = prefs.getLong(KEY_CALL_END_TIME, 0L)
        set(value) = prefs.edit().putLong(KEY_CALL_END_TIME, value).apply()

    var durationSeconds: Int
        get() = prefs.getInt(KEY_DURATION_SECONDS, 0)
        set(value) = prefs.edit().putInt(KEY_DURATION_SECONDS, value).apply()

    var isCallActive: Boolean
        get() = prefs.getBoolean(KEY_IS_CALL_ACTIVE, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_CALL_ACTIVE, value).apply()

    var lastUploadTimestamp: Long
        get() = prefs.getLong(KEY_LAST_UPLOAD_TIMESTAMP, 0L)
        set(value) = prefs.edit().putLong(KEY_LAST_UPLOAD_TIMESTAMP, value).apply()

    var isServiceActive: Boolean
        get() = prefs.getBoolean(KEY_IS_SERVICE_ACTIVE, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_SERVICE_ACTIVE, value).apply()

    /**
     * Clears intermediate call logging states after successfully reading/submitting.
     */
    fun clearCallState() {
        prefs.edit()
            .remove(KEY_OUTGOING_NUMBER)
            .remove(KEY_CALL_START_TIME)
            .remove(KEY_CALL_END_TIME)
            .remove(KEY_DURATION_SECONDS)
            .remove(KEY_IS_CALL_ACTIVE)
            .apply()
    }
}
