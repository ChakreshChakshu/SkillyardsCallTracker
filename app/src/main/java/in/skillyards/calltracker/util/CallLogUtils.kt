package `in`.skillyards.calltracker.util

import android.content.Context
import android.provider.CallLog

object CallLogUtils {
    
    fun getLastOutgoingNumber(context: Context): String? {
        val cursor = context.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            arrayOf(CallLog.Calls.NUMBER, CallLog.Calls.TYPE),
            "${CallLog.Calls.TYPE} = ?",
            arrayOf(CallLog.Calls.OUTGOING_TYPE.toString()),
            "${CallLog.Calls.DATE} DESC"
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)
                if (numberIndex != -1) {
                    return it.getString(numberIndex)
                }
            }
        }
        return null
    }
}
