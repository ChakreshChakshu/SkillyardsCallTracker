package in.skillyards.calltracker.onboarding;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 )2\u00020\u0001:\u0001)B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\rH\u0002J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0013H\u0002J\b\u0010\u001a\u001a\u00020\u0013H\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\rH\u0002J\u0010\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\rH\u0002J\b\u0010 \u001a\u00020\u0013H\u0002J\b\u0010!\u001a\u00020\u0013H\u0002J\u0012\u0010\"\u001a\u00020\u00132\b\u0010#\u001a\u0004\u0018\u00010$H\u0014J\b\u0010%\u001a\u00020\u0013H\u0002J\b\u0010&\u001a\u00020\u0013H\u0003J\b\u0010\'\u001a\u00020\u0013H\u0002J\b\u0010(\u001a\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lin/skillyards/calltracker/onboarding/OnboardingActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnActivate", "Lcom/google/android/material/button/MaterialButton;", "etApiUrl", "Lcom/google/android/material/textfield/TextInputEditText;", "etTelecallerId", "prefs", "Lin/skillyards/calltracker/prefs/CallPreferences;", "requestPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "tvLastUpload", "Landroid/widget/TextView;", "tvStatus", "tvViewLog", "activateCallTrackerService", "", "confirmClearLogs", "copyLogsToClipboard", "logs", "getMissingPermissions", "", "handleActivationFlow", "initViews", "isValidUUID", "", "uuidString", "isValidUrl", "urlString", "launchMiuiAutostartSettings", "loadSavedConfig", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "requestBatteryOptimizationExemption", "showLogsDialog", "showMiuiAutostartDialog", "updateUIState", "Companion", "app_debug"})
public final class OnboardingActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "OnboardingActivity";
    private in.skillyards.calltracker.prefs.CallPreferences prefs;
    private com.google.android.material.textfield.TextInputEditText etTelecallerId;
    private com.google.android.material.textfield.TextInputEditText etApiUrl;
    private com.google.android.material.button.MaterialButton btnActivate;
    private android.widget.TextView tvStatus;
    private android.widget.TextView tvLastUpload;
    private android.widget.TextView tvViewLog;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> requestPermissionLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private static final in.skillyards.calltracker.onboarding.OnboardingActivity.Companion Companion = null;
    
    public OnboardingActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initViews() {
    }
    
    private final void loadSavedConfig() {
    }
    
    private final void updateUIState() {
    }
    
    private final void handleActivationFlow() {
    }
    
    private final boolean isValidUUID(java.lang.String uuidString) {
        return false;
    }
    
    private final void activateCallTrackerService() {
    }
    
    private final java.util.List<java.lang.String> getMissingPermissions() {
        return null;
    }
    
    private final void requestBatteryOptimizationExemption() {
    }
    
    private final void showMiuiAutostartDialog() {
    }
    
    private final void launchMiuiAutostartSettings() {
    }
    
    @android.annotation.SuppressLint(value = {"InflateParams"})
    private final void showLogsDialog() {
    }
    
    private final void copyLogsToClipboard(java.lang.String logs) {
    }
    
    private final void confirmClearLogs() {
    }
    
    private final boolean isValidUrl(java.lang.String urlString) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lin/skillyards/calltracker/onboarding/OnboardingActivity$Companion;", "", "()V", "TAG", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}