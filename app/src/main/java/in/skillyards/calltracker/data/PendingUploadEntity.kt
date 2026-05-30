package `in`.skillyards.calltracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_uploads")
data class PendingUploadEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val telecallerId: String,
    val toNumber: String,
    val callStartTime: Long,
    val callEndTime: Long,
    val durationSeconds: Int,
    val recordingPath: String?,   // local file path, null if not found
    val recordingStatus: String,  // "found" | "not_found" | "no_dialer_support"
    val attemptCount: Int = 0,
    val lastAttemptTime: Long = 0,
    val createdAt: Long = System.currentTimeMillis()
)
