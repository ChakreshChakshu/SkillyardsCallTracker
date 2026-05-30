package `in`.skillyards.calltracker.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings

object DeviceUtils {

    /**
     * Retrieves unique secure ANDROID_ID.
     */
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: "unknown_device"
    }

    /**
     * Checks if running on Xiaomi / Redmi MIUI or HyperOS.
     */
    fun isMIUI(): Boolean {
        val manufacturer = Build.MANUFACTURER.lowercase()
        val brand = Build.BRAND.lowercase()
        return manufacturer.contains("xiaomi") || brand.contains("xiaomi") || manufacturer.contains("redmi") || brand.contains("redmi")
    }

    /**
     * Checks if running on Vivo / iQOO Funtouch OS.
     */
    fun isFuntouchOS(): Boolean {
        val manufacturer = Build.MANUFACTURER.lowercase()
        val brand = Build.BRAND.lowercase()
        return manufacturer.contains("vivo") || brand.contains("vivo") || manufacturer.contains("iqoo") || brand.contains("iqoo")
    }

    /**
     * Detects if the system default dialer is the Google Dialer.
     * Google Dialer blocks silent call recording on Android 10+.
     */
    fun isGoogleDialerDefault(context: Context): Boolean {
        return try {
            val packageManager = context.packageManager
            val intent = Intent(Intent.ACTION_DIAL)
            val resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
            val defaultDialerPackage = resolveInfo?.activityInfo?.packageName ?: ""
            defaultDialerPackage.contains("com.google.android.dialer")
        } catch (e: Exception) {
            false
        }
    }

    /**
     * String summary of current OS, device details, and OEM properties.
     */
    fun getOEMDetails(): String {
        return "Manufacturer: ${Build.MANUFACTURER}, Brand: ${Build.BRAND}, Model: ${Build.MODEL}, OS: Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
    }
}
