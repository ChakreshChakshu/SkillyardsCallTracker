package in.skillyards.calltracker.data;

/**
 * Plain Kotlin data model containing complete metadata about a finalized outbound call.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\tH\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003JQ\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020\tH\u00d6\u0001J\t\u0010#\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013\u00a8\u0006$"}, d2 = {"Lin/skillyards/calltracker/data/CallRecord;", "", "telecallerId", "", "toNumber", "callStartTime", "", "callEndTime", "durationSeconds", "", "recordingPath", "recordingStatus", "(Ljava/lang/String;Ljava/lang/String;JJILjava/lang/String;Ljava/lang/String;)V", "getCallEndTime", "()J", "getCallStartTime", "getDurationSeconds", "()I", "getRecordingPath", "()Ljava/lang/String;", "getRecordingStatus", "getTelecallerId", "getToNumber", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class CallRecord {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String telecallerId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String toNumber = null;
    private final long callStartTime = 0L;
    private final long callEndTime = 0L;
    private final int durationSeconds = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String recordingPath = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String recordingStatus = null;
    
    public CallRecord(@org.jetbrains.annotations.NotNull()
    java.lang.String telecallerId, @org.jetbrains.annotations.NotNull()
    java.lang.String toNumber, long callStartTime, long callEndTime, int durationSeconds, @org.jetbrains.annotations.Nullable()
    java.lang.String recordingPath, @org.jetbrains.annotations.NotNull()
    java.lang.String recordingStatus) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTelecallerId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getToNumber() {
        return null;
    }
    
    public final long getCallStartTime() {
        return 0L;
    }
    
    public final long getCallEndTime() {
        return 0L;
    }
    
    public final int getDurationSeconds() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRecordingPath() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRecordingStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final long component3() {
        return 0L;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final in.skillyards.calltracker.data.CallRecord copy(@org.jetbrains.annotations.NotNull()
    java.lang.String telecallerId, @org.jetbrains.annotations.NotNull()
    java.lang.String toNumber, long callStartTime, long callEndTime, int durationSeconds, @org.jetbrains.annotations.Nullable()
    java.lang.String recordingPath, @org.jetbrains.annotations.NotNull()
    java.lang.String recordingStatus) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}