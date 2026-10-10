# NoteWriter

NoteWriter is a small Android app for writing timestamped notes and saving them
as ordinary UTF-8 `.txt` files. Requires **Android 6.0 or newer** (API 23).

## Features

- Automatic local date and time, an editable header, and a large writing area.
- One **Note** button opens Android's system file picker.
- Choose a folder and filename, with a timestamped name suggested for you.
- Saved files include the date, time, header, and note text.
- No account, internet connection, or cloud service is required to use the app.
- No ads, database, background services, or network/storage permissions.

## Download and install

**Only the APK is needed to install and use NoteWriter.** You do not need the
source code, Android Studio, or developer tools.

1. Open this repository's **Releases** section and choose **v0.1.0**.
2. Under **Assets**, download **NoteWriter-v0.1.apk** to your Android phone.
   The automatic "Source code" ZIP and TAR files are for developers.
3. Open the downloaded APK from your browser or Files app, then tap **Install**.
4. If Android asks, allow installation from that browser or Files app. The setting
   is usually called **Install unknown apps**, **Allow from this source**, or
   **Unknown sources**, depending on Android version. You can revoke it afterward.
5. Open **Note Writer**.

This is the original tested **debug-signed APK**, shared unchanged for v0.1.
Android may show prompts for apps installed outside Google Play; follow your
device's instructions. See [Android's installation help](https://support.google.com/android/answer/9457058).

## Create and save a note

1. Enter a title under **Header**, then write your note under **Text**.
2. Tap **Note**. The app captures the phone's local date and time.
3. In the system picker, choose a local destination such as Documents or Downloads
   and accept or change the suggested name, for example `2026-10-10_0835.txt`.
4. Confirm saving in the picker. NoteWriter shows **Note saved** after success.

Notes live in the destination you choose, independently of NoteWriter, and remain
there if you uninstall it. Open them with any other app that supports text files.
You can also copy or upload them elsewhere yourself; NoteWriter does not sync them.
Saving uses Android's create-document flow to avoid silently replacing an existing
file. Canceling or failing to save keeps the entered text in the editor.

Save before closing the app: v0.1 has no permanent storage for unsaved drafts.
A failed save may leave an empty or partial file in the selected destination.

## Tested

The project owner confirmed that v0.1 runs on a physical Android phone, saves a
`.txt` note, and that the file opens outside NoteWriter and can be viewed in
Proton Drive. Proton Drive is optional and is not integrated into the app.

## Build from source

The app uses **Kotlin and standard Android UI controls**, with no additional UI
framework. Build tools: Gradle **9.6.0** (included wrapper), Android Gradle Plugin
**9.4.0** with built-in Kotlin, Android SDK Platform **37**, and Build Tools
**36.0.0**. Target SDK is 36; minimum SDK is 23.

1. Install Android Studio with its bundled JDK, then use SDK Manager to install
   the SDK platform and build tools listed above. The existing build was produced
   with Android Studio's JDK 25.
2. Clone or download this repository and open its root folder in Android Studio.
   Let it configure the SDK path and complete Gradle sync. For command-line builds,
   set `JAVA_HOME` to your compatible JDK and `ANDROID_HOME` to your Android SDK,
   or set `sdk.dir` in a local `local.properties` file. Do not commit that file.
3. Run from the project root:

   ```powershell
   # Windows PowerShell
   .\gradlew.bat --no-daemon :app:assembleDebug
   ```

   ```sh
   # macOS / Linux
   sh ./gradlew --no-daemon :app:assembleDebug
   ```

The APK appears at `app/build/outputs/apk/debug/app-debug.apk`. The first build
needs internet access to download build dependencies; the installed app does not.
Locally built debug APKs can use a different signing key from the published APK.

## Credits

**Project creator and copyright holder**

- Andreas Andersson — Original concept, project direction, requirements, and testing.

**Development**

- Astra (OpenAI Codex) — Android application implementation, Kotlin development, and build configuration.
- Sol / Atlas (OpenAI ChatGPT) — Project planning, technical guidance, requirements, and development coordination.

Astra and Sol / Atlas are AI assistants used during development, not additional
copyright holders.

## Acknowledgements

**Special shoutout to GitKraken!**

A nod to GitKraken for its excellent visual approach to Git and developer workflows.
Tools that make software development more approachable and enjoyable deserve recognition.

Website: [GitKraken](https://www.gitkraken.com/)

This acknowledgement does not imply sponsorship, endorsement of NoteWriter by
GitKraken, or an official partnership.

## License

NoteWriter's original source is licensed under the [MIT License](LICENSE).

Copyright (c) 2026 Andreas Andersson

Bundled third-party components retain their own licenses.
