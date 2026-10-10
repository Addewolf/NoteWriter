plugins { id("com.android.application") }

android {
    namespace = "se.adde.notewriter"
    compileSdk = 37

    defaultConfig {
        applicationId = "se.adde.notewriter"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "0.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
