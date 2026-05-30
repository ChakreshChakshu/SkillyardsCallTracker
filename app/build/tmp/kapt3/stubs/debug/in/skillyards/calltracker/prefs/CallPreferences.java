package in.skillyards.calltracker.prefs;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 /2\u00020\u0001:\u0001/B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010-\u001a\u00020.R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR$\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R$\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R$\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u00158F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR$\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0005\u001a\u00020\u001b8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR$\u0010 \u001a\u00020\u001b2\u0006\u0010\u0005\u001a\u00020\u001b8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b \u0010\u001d\"\u0004\b!\u0010\u001fR$\u0010\"\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b#\u0010\u000f\"\u0004\b$\u0010\u0011R(\u0010%\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b&\u0010\t\"\u0004\b\'\u0010\u000bR\u000e\u0010(\u001a\u00020)X\u0082\u0004\u00a2\u0006\u0002\n\u0000R(\u0010*\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b+\u0010\t\"\u0004\b,\u0010\u000b\u00a8\u00060"}, d2 = {"Lin/skillyards/calltracker/prefs/CallPreferences;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "value", "", "apiBaseUrl", "getApiBaseUrl", "()Ljava/lang/String;", "setApiBaseUrl", "(Ljava/lang/String;)V", "", "callEndTime", "getCallEndTime", "()J", "setCallEndTime", "(J)V", "callStartTime", "getCallStartTime", "setCallStartTime", "", "durationSeconds", "getDurationSeconds", "()I", "setDurationSeconds", "(I)V", "", "isCallActive", "()Z", "setCallActive", "(Z)V", "isServiceActive", "setServiceActive", "lastUploadTimestamp", "getLastUploadTimestamp", "setLastUploadTimestamp", "outgoingNumber", "getOutgoingNumber", "setOutgoingNumber", "prefs", "Landroid/content/SharedPreferences;", "telecallerId", "getTelecallerId", "setTelecallerId", "clearCallState", "", "Companion", "app_debug"})
public final class CallPreferences {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_TELECALLER_ID = "telecaller_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_API_BASE_URL = "api_base_url";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_OUTGOING_NUMBER = "outgoing_number";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_CALL_START_TIME = "call_start_time";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_CALL_END_TIME = "call_end_time";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DURATION_SECONDS = "duration_seconds";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_IS_CALL_ACTIVE = "is_call_active";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LAST_UPLOAD_TIMESTAMP = "last_upload_timestamp";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_IS_SERVICE_ACTIVE = "is_service_active";
    @org.jetbrains.annotations.NotNull()
    public static final in.skillyards.calltracker.prefs.CallPreferences.Companion Companion = null;
    
    public CallPreferences(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTelecallerId() {
        return null;
    }
    
    public final void setTelecallerId(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getApiBaseUrl() {
        return null;
    }
    
    public final void setApiBaseUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getOutgoingNumber() {
        return null;
    }
    
    public final void setOutgoingNumber(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    public final long getCallStartTime() {
        return 0L;
    }
    
    public final void setCallStartTime(long value) {
    }
    
    public final long getCallEndTime() {
        return 0L;
    }
    
    public final void setCallEndTime(long value) {
    }
    
    public final int getDurationSeconds() {
        return 0;
    }
    
    public final void setDurationSeconds(int value) {
    }
    
    public final boolean isCallActive() {
        return false;
    }
    
    public final void setCallActive(boolean value) {
    }
    
    public final long getLastUploadTimestamp() {
        return 0L;
    }
    
    public final void setLastUploadTimestamp(long value) {
    }
    
    public final boolean isServiceActive() {
        return false;
    }
    
    public final void setServiceActive(boolean value) {
    }
    
    /**
     * Clears intermediate call logging states after successfully reading/submitting.
     */
    public final void clearCallState() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lin/skillyards/calltracker/prefs/CallPreferences$Companion;", "", "()V", "KEY_API_BASE_URL", "", "KEY_CALL_END_TIME", "KEY_CALL_START_TIME", "KEY_DURATION_SECONDS", "KEY_IS_CALL_ACTIVE", "KEY_IS_SERVICE_ACTIVE", "KEY_LAST_UPLOAD_TIMESTAMP", "KEY_OUTGOING_NUMBER", "KEY_TELECALLER_ID", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}