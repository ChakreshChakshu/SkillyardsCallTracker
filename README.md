# SkillYards Call Tracker (Android)

An automated background utility for telecallers that tracks outbound calls, retrieves dialed numbers, matches audio recordings, and syncs data to the SkillYards CRM.

## 🚀 Key Features

*   **Real-time Call Detection**: Automatically triggers tracking when a call ends.
*   **Android 15 Optimized**: Fully compliant with Scoped Storage and Foreground Service restrictions.
*   **Smart Number Recovery**: Bypasses Android's privacy masks by recovering dialed numbers directly from the system Call Log.
*   **Smart Match Recording Finder**: Uses high-precision timestamp matching to find recordings even when they are named after "Contact Names" (specifically optimized for Vivo/iQOO devices).
*   **Offline-First**: Stores call data in a local Room database if the network is unavailable, with automatic retries via WorkManager.
*   **Base64 Audio Sync**: Automatically encodes call recordings to Base64 for seamless CRM integration.
*   **In-App Diagnostics**: Built-in log viewer for administrators to verify sync status and troubleshooting.

## 🛠 Tech Stack

*   **Language**: Kotlin
*   **Local Database**: Room (SQLite)
*   **Background Jobs**: WorkManager
*   **Networking**: OkHttp
*   **OS Support**: Android 8.0 (API 26) to Android 15 (API 35)

## 📲 Setup & Installation

Since this app uses sensitive privacy permissions (Call Logs/Phone), it is distributed manually via APK.

### 1. Initial Installation
1.  Download and install the `app-debug.apk`.
2.  If prompted by "Play Protect," tap **Install anyway**.

### 2. Required App Settings (Crucial for background tracking)
To ensure the app isn't killed by the Android system, follow these steps:
*   **Permissions**: Grant all 4 requested permissions (Phone, Call Logs, Notifications, and Music/Audio).
*   **Battery Optimization**: Set the app to **Unrestricted** or **Don't Optimize**.
*   **Autostart (Vivo/Xiaomi)**: Enable "Autostart" in the app's settings or system security center.

### 3. Configuration
On the onboarding screen:
*   **Telecaller ID**: Enter your assigned UUID
*   Tap **Activate Call Tracker**.

## 🔗 CRM Integration

### Sample Payload
```json
{
  "telecaller_id": "da4d0da0-6e3c-4cab-b611-xxxxxxxx",
  "to_number": "+9198765xxxx",
  "call_duration_seconds": 45,
  "call_start_time": "2026-05-30T10:00:00Z",
  "call_direction": "outgoing",
  "recording_base64": "/9j/4AAQSkZJRg...",
  "recording_ext": "m4a"
}
```

## 🏗 Development & Build

1.  Clone the repository:
    ```bash
    git clone git@github.com:ChakreshChakshu/SkillyardsCallTracker.git
    ```
2.  Open in **Android Studio Hedgehog** or newer.

    ```properties
4.  Build the APK:
    ```bash
    ./gradlew :app:assembleDebug
    ```
