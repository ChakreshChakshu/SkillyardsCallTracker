# Proguard rules for SkillYards Call Tracker

# Keep Room DB Components
-keep class * extends androidx.room.RoomDatabase
-keepclassmembers class * {
    @androidx.room.Database *;
    @androidx.room.Dao *;
    @androidx.room.Entity *;
}

# OkHttp optimization exclusions
-keepattributes Signature, InnerClasses, AnnotationDefault, EnclosingMethod
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**

# Keep our custom data layer and WorkManager classes
-keep class in.skillyards.calltracker.data.** { *; }
-keep class in.skillyards.calltracker.worker.** { *; }
