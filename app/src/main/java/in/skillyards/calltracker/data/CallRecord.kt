package `in`.skillyards.calltracker.data

/**
 * Plain Kotlin data model containing complete metadata about a finalized outbound call.
 */
data class CallRecord(
    val telecallerId: String,
    val toNumber: String,
    val callStartTime: Long,
    val callEndTime: Long,
    val durationSeconds: Int,
    val recordingPath: String?,
    val recordingStatus: String // "found" | "not_found" | "no_dialer_support"
)
