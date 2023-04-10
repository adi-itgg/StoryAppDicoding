plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version("1.8.10-1.0.9")
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
            proguardFiles("proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":app"))
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
    compileOnly("androidx.core:core-ktx:$coreKTX_version")
    compileOnly("androidx.appcompat:appcompat:$appCompat_version")
    compileOnly("com.google.android.material:material:$material_version")
    compileOnly("androidx.constraintlayout:constraintlayout:$constraintLayout_version")
    compileOnly("androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshLayout_version")
    // ==============================  Splash  ===============================
    compileOnly("androidx.core:core-splashscreen:$splashScreen_version")
    // ===============================  Maps  ================================
    compileOnly("com.google.android.gms:play-services-maps:$googleMaps_version")
    // =============================  Location  ==============================
    compileOnly("com.google.android.gms:play-services-location:$location_version")
    // ===============================  Koin  ================================
    compileOnly("io.insert-koin:koin-core:$koin_version")
    compileOnly("io.insert-koin:koin-android:$koin_android_version")
    compileOnly("io.insert-koin:koin-annotations:$koin_ksp_version")
    // ========================= Jetpack WorkManager =========================
    compileOnly("io.insert-koin:koin-androidx-workmanager:$koin_android_version")
    // ============================ Koin for Ktor ============================
    compileOnly("io.insert-koin:koin-ktor:$koin_ktor")
    // ============================== Retrofit ===============================
    compileOnly("com.squareup.retrofit2:retrofit:$retrofit_version")
    compileOnly("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$retrofitJson_version")
    compileOnly("com.squareup.okhttp3:logging-interceptor:$retrofitLogging_version")
    // ======================== Kotlinx Serialization ========================
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:$ktxJsonSerializationVersion")
    // ======================== Preferences DataStore ========================
    compileOnly("androidx.datastore:datastore-preferences:$preferenceDataStore_version")
    // ================================ Coil =================================
    compileOnly("io.coil-kt:coil:$coil_version")
    // ========================== Image Compressor ===========================
    compileOnly("id.zelory:compressor:$imageCompressor_version")
    // ==========================    Gif Player    ===========================
    compileOnly("pl.droidsonroids.gif:android-gif-drawable:$gifDrawable_version")
    // ==========================       Paging     ===========================
    compileOnly("androidx.paging:paging-runtime-ktx:$paging_version")
    // ========================    Room     ========================
    compileOnly("androidx.room:room-runtime:$room_version")
    compileOnly("androidx.room:room-ktx:$room_version")
    compileOnly("androidx.room:room-paging:$room_version")
    compileOnly("net.zetetic:android-database-sqlcipher:4.5.3")
    // ======================   Espresso    =======================
    compileOnly("androidx.test.espresso:espresso-idling-resource:$androidEkspresso_version")
    compileOnly("com.google.android.play:core:1.10.3")


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
}