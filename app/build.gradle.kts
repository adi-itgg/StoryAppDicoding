plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version("1.8.10-1.0.9")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "me.syahdilla.putra.sholeh.storyappdicoding"
    compileSdk = 32

    defaultConfig {
        applicationId = "me.syahdilla.putra.sholeh.storyappdicoding"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
        release {
            isMinifyEnabled = true
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
    dynamicFeatures += setOf(":favorit")
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

    implementation(project(":core"))

    val koin_version = "3.2.2"
    val koin_android_version = "3.3.0"
    val koin_ksp_version = "1.1.1"
    val koin_ktor= "3.2.2"
    val retrofit_version = "2.9.0"
    val retrofitJson_version = "0.8.0"
    val retrofitLogging_version = "4.10.0"
    val ktxJsonSerializationVersion = "1.4.1"
    val coreKTX_version = "1.8.0"
    val appCompat_version = "1.5.1"
    val material_version = "1.7.0"
    val constraintLayout_version = "2.1.4"
    val swipeRefreshLayout_version = "1.1.0"
    val preferenceDataStore_version = "1.0.0"
    val coil_version = "2.2.2"
    val imageCompressor_version = "3.0.1"
    val splashScreen_version = "1.0.0"
    val gifDrawable_version = "1.2.25"
    val paging_version = "3.1.1"
    val googleMaps_version = "18.1.0"
    val location_version = "21.0.1"
    val androidEkspresso_version = "3.5.1"
    val coroutineVersion = "1.6.4"
    val jUnitVersion = "4.13.2"
    val mockitoVersion = "4.9.0"
    val kotlinMockitoVersion = "4.1.0"
    val room_version = "2.4.3"
    // default dependency
    implementation("androidx.core:core-ktx:$coreKTX_version")
    implementation("androidx.appcompat:appcompat:$appCompat_version")
    implementation("com.google.android.material:material:$material_version")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayout_version")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshLayout_version")
    // ==============================  Splash  ===============================
    implementation("androidx.core:core-splashscreen:$splashScreen_version")
    // ===============================  Maps  ================================
    implementation("com.google.android.gms:play-services-maps:$googleMaps_version")
    // =============================  Location  ==============================
    implementation("com.google.android.gms:play-services-location:$location_version")
    // ===============================  Koin  ================================
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-android:$koin_android_version")
    implementation("io.insert-koin:koin-annotations:$koin_ksp_version")
    // ========================= Jetpack WorkManager =========================
    implementation("io.insert-koin:koin-androidx-workmanager:$koin_android_version")
    // ============================ Koin for Ktor ============================
    implementation("io.insert-koin:koin-ktor:$koin_ktor")
    // ============================== Retrofit ===============================
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$retrofitJson_version")
    implementation("com.squareup.okhttp3:logging-interceptor:$retrofitLogging_version")
    // ======================== Kotlinx Serialization ========================
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$ktxJsonSerializationVersion")
    // ======================== Preferences DataStore ========================
    implementation("androidx.datastore:datastore-preferences:$preferenceDataStore_version")
    // ================================ Coil =================================
    implementation("io.coil-kt:coil:$coil_version")
    // ========================== Image Compressor ===========================
    implementation("id.zelory:compressor:$imageCompressor_version")
    // ==========================    Gif Player    ===========================
    implementation("pl.droidsonroids.gif:android-gif-drawable:$gifDrawable_version")
    // ==========================       Paging     ===========================
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")
    // ========================    Room     ========================
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-paging:$room_version")
    implementation("net.zetetic:android-database-sqlcipher:4.5.3")
    // ======================   Espresso    =======================
    implementation("androidx.test.espresso:espresso-idling-resource:$androidEkspresso_version")
    implementation("com.google.android.play:core:1.10.3")


    ksp("androidx.room:room-compiler:$room_version")
    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version")


    // =========================   Core    =========================
    testImplementation("junit:junit:$jUnitVersion")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    // ========================  Coroutine  ========================
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion")
    // ========================    Koin     ========================
    testImplementation("io.insert-koin:koin-test:$koin_version")
    testImplementation("io.insert-koin:koin-test-junit4:$koin_version")
    // ========================    Mock     ========================
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-inline:$mockitoVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$kotlinMockitoVersion")
    // ========================    Room     ========================
    testImplementation("androidx.room:room-testing:$room_version")


    // =========================   Core    =========================
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    // ======================   Espresso    =======================
    androidTestImplementation("androidx.test.espresso:espresso-core:$androidEkspresso_version")
    androidTestImplementation("androidx.test.espresso:espresso-intents:$androidEkspresso_version")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:$androidEkspresso_version")
    // ========================  Coroutine  ========================
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion")


    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")

}