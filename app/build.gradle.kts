plugins {
    id(Plugin.Android.App)
    id(Plugin.Android.Kotlin)
    id(Plugin.KotlinXSerialization)
    id(Plugin.Parcelize)
    id(Plugin.Kapt)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = APP_PACKAGE
    compileSdk = 32

    defaultConfig {
        applicationId = APP_PACKAGE
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            type = "String",
            name = "BASE_API_URL",
            value = "\"https://story-api.dicoding.dev/v1/\""
        )
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        animationsDisabled = true
    }
}

dependencies {

    implements(Deps)
    implements(DepsTest, Type.TEST)
    implements(DepsAndroidTest, Type.ANDROID_TEST)
    implements(Kapt, Type.KAPT)

}