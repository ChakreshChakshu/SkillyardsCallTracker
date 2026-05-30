package `in`.skillyards.calltracker.util

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Logger {
    private const val LOG_FILE_NAME = "skillyards_calltracker.log"
    private const val MAX_LINES = 500

    fun d(context: Context, tag: String, message: String) = log(context, "DEBUG", tag, message)
    fun i(context: Context, tag: String, message: String) = log(context, "INFO", tag, message)
    fun w(context: Context, tag: String, message: String) = log(context, "WARN", tag, message)
    fun e(context: Context, tag: String, message: String, throwable: Throwable? = null) {
        val msg = if (throwable != null) "$message\n${throwable.stackTraceToString()}" else message
        log(context, "ERROR", tag, msg)
    }

    @Synchronized
    private fun log(context: Context, level: String, tag: String, message: String) {
        // Also print to system logcat for ADB debugging
        when(level) {
            "DEBUG" -> android.util.Log.d(tag, message)
            "INFO" -> android.util.Log.i(tag, message)
            "WARN" -> android.util.Log.w(tag, message)
            "ERROR" -> android.util.Log.e(tag, message)
        }

        try {
            val file = File(context.filesDir, LOG_FILE_NAME)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val timestamp = sdf.format(Date())
            val line = "[$timestamp] [$level] [$tag] $message"

            // Read existing lines if any
            var lines = mutableListOf<String>()
            if (file.exists()) {
                lines = file.readLines().toMutableList()
            }

            // Append new line
            lines.add(line)

            // Rotate if exceeded MAX_LINES (keep last 500)
            if (lines.size > MAX_LINES) {
                lines = lines.subList(lines.size - MAX_LINES, lines.size)
            }

            file.writeText(lines.joinToString("\n") + "\n")
        } catch (e: Exception) {
            android.util.Log.e("Logger", "Failed to write to file log", e)
        }
    }

    @Synchronized
    fun getLogContent(context: Context): String {
        val file = File(context.filesDir, LOG_FILE_NAME)
        return if (file.exists()) {
            file.readText()
        } else {
            "No logs recorded yet."
        }
    }

    @Synchronized
    fun clearLogs(context: Context) {
        val file = File(context.filesDir, LOG_FILE_NAME)
        if (file.exists()) {
            file.delete()
        }
    }
}
