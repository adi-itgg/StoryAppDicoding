plugins {
    id("com.android.dynamic-feature")
    id(Plugin.Android.Kotlin)
    id(Plugin.KotlinXSerialization)
    id(Plugin.Parcelize)
    id(Plugin.Ksp) version(KspVersion)
}
android {
    namespace = "me.syahdilla.putra.sholeh.favorit"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":core"))
    implements(Deps)
    implements(DepsTest, Type.TEST)
    implements(DepsAndroidTest, Type.ANDROID_TEST)
    implements(KSP, Type.KSP)
}