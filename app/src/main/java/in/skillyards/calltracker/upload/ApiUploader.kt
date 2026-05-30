package `in`.skillyards.calltracker.upload

import android.content.Context
import android.util.Base64
import `in`.skillyards.calltracker.BuildConfig
import `in`.skillyards.calltracker.data.CallRecord
import `in`.skillyards.calltracker.util.Logger
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object ApiUploader {
    private const val TAG = "ApiUploader"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    fun uploadCallRecord(
        context: Context,
        apiBaseUrl: String,
        record: CallRecord
    ): Boolean {
        val url = "${apiBaseUrl.trimEnd('/')}/api/telephony/gsm-callback"
        Logger.i(context, TAG, "Uploading to CRM: $url")

        try {
            val json = JSONObject()
            json.put("telecaller_id", record.telecallerId)
            json.put("to_number", record.toNumber)
            json.put("call_duration_seconds", record.durationSeconds)
            json.put("call_direction", "outgoing")
            
            // Format time to ISO 8601 (e.g. 2026-05-30T13:00:00Z)
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            json.put("call_start_time", sdf.format(Date(record.callStartTime)))

            // Convert file to Base64 if found
            if (record.recordingStatus == "found" && !record.recordingPath.isNullOrEmpty()) {
                val file = File(record.recordingPath)
                if (file.exists()) {
                    val bytes = file.readBytes()
                    val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
                    json.put("recording_base64", base64)
                    json.put("recording_ext", file.extension.ifEmpty { "m4a" })
                    Logger.d(context, TAG, "Attached recording as Base64 (${bytes.size} bytes)")
                }
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body = json.toString().toRequestBody(mediaType)

            val request = Request.Builder()
                .url(url)
                .addHeader("x-app-secret", BuildConfig.APP_SECRET)
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    Logger.i(context, TAG, "CRM Upload SUCCESS: $responseBody")
                    return true
                } else {
                    Logger.e(context, TAG, "CRM Upload FAILED (${response.code}): $responseBody")
                    return false
                }
            }
        } catch (e: Exception) {
            Logger.e(context, TAG, "Network Error: ${e.message}", e)
            return false
        }
    }
}
