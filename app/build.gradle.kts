plugins {
    id(Plugin.Android.App)
    id(Plugin.Android.Kotlin)
    id(Plugin.KotlinXSerialization)
    id(Plugin.Parcelize)
    id(Plugin.Ksp) version(KspVersion)
    id(Plugin.GoogleMaps)
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

    sourceSets.getByName("main").java.srcDir("src/main/kotlin")
    sourceSets.getByName("test").java.srcDir("src/test/kotlin")
    // For KSP
    applicationVariants.configureEach {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

}

dependencies {

    implements(Deps)
    implements(DepsTest, Type.TEST)
    implements(DepsAndroidTest, Type.ANDROID_TEST)
    implements(KSP, Type.KSP)

}