package `in`.skillyards.calltracker.recording

import android.content.Context
import android.provider.MediaStore
import `in`.skillyards.calltracker.util.Logger
import kotlinx.coroutines.delay
import java.io.File
import kotlin.math.abs

object RecordingFinder {
    private const val TAG = "RecordingFinder"

    /**
     * Finds the call recording using MediaStore.
     * Uses a multi-stage matching logic to handle files named by Contact Name or Number.
     */
    suspend fun findCallRecording(
        context: Context,
        outgoingNumber: String,
        callEndTime: Long
    ): File? {
        val cleanNumber = outgoingNumber.filter { it.isDigit() }
        if (cleanNumber.isEmpty()) return null

        val matchQuery = if (cleanNumber.length >= 10) {
            cleanNumber.substring(cleanNumber.length - 10)
        } else {
            cleanNumber
        }

        Logger.i(context, TAG, "Starting Smart Search. Target: '$matchQuery', Call End: $callEndTime")

        // Wait for system to index the file
        delay(4000)

        val pollIntervalMs = 5000L
        val maxDurationMs = 60000L
        var elapsedMs = 4000L

        while (elapsedMs <= maxDurationMs) {
            val projection = arrayOf(
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DATE_MODIFIED
            )

            // Look for files from today
            val selection = "${MediaStore.Audio.Media.DATE_MODIFIED} > ?"
            val selectionArgs = arrayOf(((System.currentTimeMillis() - 86400000) / 1000).toString())
            
            context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                "${MediaStore.Audio.Media.DATE_MODIFIED} DESC"
            )?.use { cursor ->
                val nameCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                val dataCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                val dateCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)

                while (cursor.moveToNext()) {
                    val fileName = cursor.getString(nameCol)
                    val filePath = cursor.getString(dataCol)
                    val dateModified = cursor.getLong(dateCol) * 1000
                    val timeDiff = abs(dateModified - callEndTime) / 1000

                    // Log candidates
                    if (timeDiff < 60) {
                        Logger.d(context, TAG, "Candidate found: $fileName (TimeDiff: ${timeDiff}s)")
                    }

                    // MATCH LOGIC:
                    // 1. Filename contains the phone number
                    // 2. OR Filename was modified within 20 seconds of call end (Strong match for Contact names)
                    if (timeDiff <= 20 || fileName.contains(matchQuery)) {
                        val file = File(filePath)
                        if (file.exists()) {
                            Logger.i(context, TAG, "SUCCESS: Found recording via Smart Match: $fileName")
                            return file
                        }
                    }
                }
            }

            delay(pollIntervalMs)
            elapsedMs += pollIntervalMs
        }

        Logger.w(context, TAG, "Recording search timed out.")
        return null
    }
}
