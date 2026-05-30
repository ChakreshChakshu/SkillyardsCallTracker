package `in`.skillyards.calltracker.onboarding

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import `in`.skillyards.calltracker.R
import `in`.skillyards.calltracker.prefs.CallPreferences
import `in`.skillyards.calltracker.util.DeviceUtils
import `in`.skillyards.calltracker.util.Logger
import `in`.skillyards.calltracker.worker.RetryUploadWorker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class OnboardingActivity : AppCompatActivity() {

    private companion object {
        private const val TAG = "OnboardingActivity"
    }

    private lateinit var prefs: CallPreferences

    private lateinit var etTelecallerId: TextInputEditText
    private lateinit var etApiUrl: TextInputEditText
    private lateinit var btnActivate: MaterialButton
    private lateinit var tvStatus: TextView
    private lateinit var tvLastUpload: TextView
    private lateinit var tvViewLog: TextView

    // Dynamic dynamic permission request launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val allGranted = results.values.all { it }
        if (allGranted) {
            Logger.i(this, TAG, "All required runtime permissions granted by user.")
            activateCallTrackerService()
        } else {
            Logger.w(this, TAG, "Permissions check failed: Some runtime permissions were denied.")
            Toast.makeText(this, R.string.toast_permissions_needed, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        prefs = CallPreferences(this)
        Logger.i(this, TAG, "Onboarding screen launched. ${DeviceUtils.getOEMDetails()}")

        initViews()
        loadSavedConfig()
        updateUIState()
    }

    private fun initViews() {
        etTelecallerId = findViewById(R.id.etTelecallerId)
        etApiUrl = findViewById(R.id.etApiUrl)
        btnActivate = findViewById(R.id.btnActivate)
        tvStatus = findViewById(R.id.tvStatus)
        tvLastUpload = findViewById(R.id.tvLastUpload)
        tvViewLog = findViewById(R.id.tvViewLog)

        btnActivate.setOnClickListener {
            handleActivationFlow()
        }

        tvViewLog.setOnClickListener {
            showLogsDialog()
        }
    }

    private fun loadSavedConfig() {
        etTelecallerId.setText(prefs.telecallerId)
        etApiUrl.setText(prefs.apiBaseUrl)
    }

    private fun updateUIState() {
        if (prefs.isServiceActive) {
            tvStatus.text = getString(R.string.status_active)
            tvStatus.setTextColor(ContextCompat.getColor(this, R.color.state_active))
            btnActivate.text = getString(R.string.btn_activate)
        } else {
            tvStatus.text = getString(R.string.status_inactive)
            tvStatus.setTextColor(ContextCompat.getColor(this, R.color.state_inactive))
            btnActivate.text = getString(R.string.btn_activate)
        }

        if (prefs.lastUploadTimestamp > 0L) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            tvLastUpload.text = sdf.format(Date(prefs.lastUploadTimestamp))
        } else {
            tvLastUpload.text = getString(R.string.last_upload_never)
        }
    }

    private fun handleActivationFlow() {
        val telecallerId = etTelecallerId.text?.toString()?.trim() ?: ""
        val apiUrl = etApiUrl.text?.toString()?.trim() ?: ""

        // 1. Validate Telecaller ID (Must be a valid UUID for CRM matching)
        if (!isValidUUID(telecallerId)) {
            Toast.makeText(this, "Please enter a valid Telecaller UUID", Toast.LENGTH_SHORT).show()
            return
        }

        // 2. Validate Endpoint URL structure
        if (!isValidUrl(apiUrl)) {
            Toast.makeText(this, R.string.toast_invalid_url, Toast.LENGTH_SHORT).show()
            return
        }

        // Save valid configurations
        prefs.telecallerId = telecallerId
        prefs.apiBaseUrl = apiUrl

        // 3. Verify permissions first
        val missingPermissions = getMissingPermissions()
        if (missingPermissions.isNotEmpty()) {
            Logger.i(this, TAG, "Requesting missing permissions: ${missingPermissions.joinToString()}")
            requestPermissionLauncher.launch(missingPermissions.toTypedArray())
        } else {
            activateCallTrackerService()
        }
    }

    private fun isValidUUID(uuidString: String): Boolean {
        return try {
            java.util.UUID.fromString(uuidString)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun activateCallTrackerService() {
        prefs.isServiceActive = true
        updateUIState()
        Logger.i(this, TAG, "Call Tracker activated. Configurations committed: UUID=${prefs.telecallerId}, URL=${prefs.apiBaseUrl}")

        // 1. Enqueue WorkManager Retry worker
        RetryUploadWorker.schedule(this)

        // 2. Request exemption from standard battery optimization algorithms
        requestBatteryOptimizationExemption()

        // 3. Request MIUI Autostart configuration if running on a Xiaomi device
        if (DeviceUtils.isMIUI()) {
            showMiuiAutostartDialog()
        } else {
            Toast.makeText(this, R.string.toast_activated, Toast.LENGTH_LONG).show()
        }
    }

    private fun getMissingPermissions(): List<String> {
        val required = mutableListOf(
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_CALL_LOG
        )

        // PROCESS_OUTGOING_CALLS required for older devices
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            required.add(android.Manifest.permission.PROCESS_OUTGOING_CALLS)
        }

        // Handle scoped storage checks based on Android SDK level
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            required.add(android.Manifest.permission.READ_MEDIA_AUDIO)
            required.add(android.Manifest.permission.POST_NOTIFICATIONS) // Android 13+ Foreground requirement
        } else {
            required.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        return required.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestBatteryOptimizationExemption() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as? PowerManager ?: return
        val packageName = packageName
        
        if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
            Logger.i(this, TAG, "Battery Optimization exemption requested.")
            try {
                @SuppressLint("BatteryLife")
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                    data = Uri.parse("package:$packageName")
                }
                startActivity(intent)
            } catch (e: Exception) {
                Logger.e(this, TAG, "Failed to launch battery exemption intent", e)
            }
        } else {
            Logger.d(this, TAG, "Device is already exempt from battery optimization.")
        }
    }

    private fun showMiuiAutostartDialog() {
        Logger.i(this, TAG, "Displaying MIUI Autostart prompt dialog.")
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.autostart_dialog_title)
            .setMessage(R.string.autostart_dialog_msg)
            .setCancelable(false)
            .setPositiveButton(R.string.btn_open_settings) { _, _ ->
                launchMiuiAutostartSettings()
                Toast.makeText(this, R.string.toast_activated, Toast.LENGTH_LONG).show()
            }
            .setNegativeButton(R.string.btn_dismiss) { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, R.string.toast_activated, Toast.LENGTH_LONG).show()
            }
            .show()
    }

    private fun launchMiuiAutostartSettings() {
        try {
            Logger.i(this, TAG, "Redirecting Xiaomi user to autostart permission center.")
            val intent = Intent("miui.intent.action.APP_PERM_EDITOR").apply {
                setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity")
                putExtra("extra_pkgname", packageName)
            }
            startActivity(intent)
        } catch (e1: Exception) {
            try {
                // Secondary fallback deep link structure for alternative MIUI iterations
                val intent = Intent().apply {
                    setClassName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")
                }
                startActivity(intent)
            } catch (e2: Exception) {
                Logger.e(this, TAG, "Failed to resolve MIUI settings intents. Directing to standard app info page.", e2)
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:$packageName")
                }
                startActivity(intent)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun showLogsDialog() {
        Logger.d(this, TAG, "Displaying diagnostics log viewer dialog.")
        val logs = Logger.getLogContent(this)
        
        val dialogView = layoutInflater.inflate(R.layout.dialog_logs_viewer, null)
        val tvLogsContent = dialogView.findViewById<TextView>(R.id.tvLogsContent)
        tvLogsContent.text = logs

        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("SkillYards System Logs")
            .setView(dialogView)
            .setCancelable(true)
            .setPositiveButton("Close", null)
            .setNeutralButton("Copy Logs") { _, _ ->
                copyLogsToClipboard(logs)
            }
            .setNegativeButton("Clear Logs") { _, _ ->
                confirmClearLogs()
            }
            .create()

        dialog.show()
    }

    private fun copyLogsToClipboard(logs: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("SkillYardsCallTrackerLogs", logs)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Logs copied to clipboard.", Toast.LENGTH_SHORT).show()
        Logger.i(this, TAG, "Diagnostics log copied to clipboard by administrator.")
    }

    private fun confirmClearLogs() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Clear Diagnostics Logs")
            .setMessage("Are you sure you want to delete all local system logs? This action is irreversible.")
            .setPositiveButton("Clear") { _, _ ->
                Logger.clearLogs(this)
                Logger.i(this, TAG, "Diagnostics log manually wiped by administrator.")
                Toast.makeText(this, "Logs cleared successfully.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun isValidUrl(urlString: String): Boolean {
        return urlString.startsWith("http://") || urlString.startsWith("https://")
    }
}
