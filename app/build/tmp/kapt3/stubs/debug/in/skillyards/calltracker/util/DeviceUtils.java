package in.skillyards.calltracker.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0004J\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u000b\u001a\u00020\t\u00a8\u0006\f"}, d2 = {"Lin/skillyards/calltracker/util/DeviceUtils;", "", "()V", "getDeviceId", "", "context", "Landroid/content/Context;", "getOEMDetails", "isFuntouchOS", "", "isGoogleDialerDefault", "isMIUI", "app_debug"})
public final class DeviceUtils {
    @org.jetbrains.annotations.NotNull()
    public static final in.skillyards.calltracker.util.DeviceUtils INSTANCE = null;
    
    private DeviceUtils() {
        super();
    }
    
    /**
     * Retrieves unique secure ANDROID_ID.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceId(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * Checks if running on Xiaomi / Redmi MIUI or HyperOS.
     */
    public final boolean isMIUI() {
        return false;
    }
    
    /**
     * Checks if running on Vivo / iQOO Funtouch OS.
     */
    public final boolean isFuntouchOS() {
        return false;
    }
    
    /**
     * Detects if the system default dialer is the Google Dialer.
     * Google Dialer blocks silent call recording on Android 10+.
     */
    public final boolean isGoogleDialerDefault(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    /**
     * String summary of current OS, device details, and OEM properties.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOEMDetails() {
        return null;
    }
}