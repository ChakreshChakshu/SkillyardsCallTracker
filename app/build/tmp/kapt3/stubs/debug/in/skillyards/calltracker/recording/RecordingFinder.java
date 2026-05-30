package in.skillyards.calltracker.recording;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lin/skillyards/calltracker/recording/RecordingFinder;", "", "()V", "TAG", "", "findCallRecording", "Ljava/io/File;", "context", "Landroid/content/Context;", "outgoingNumber", "callEndTime", "", "(Landroid/content/Context;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class RecordingFinder {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "RecordingFinder";
    @org.jetbrains.annotations.NotNull()
    public static final in.skillyards.calltracker.recording.RecordingFinder INSTANCE = null;
    
    private RecordingFinder() {
        super();
    }
    
    /**
     * Finds the call recording using MediaStore.
     * Uses a multi-stage matching logic to handle files named by Contact Name or Number.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object findCallRecording(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String outgoingNumber, long callEndTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.io.File> $completion) {
        return null;
    }
}