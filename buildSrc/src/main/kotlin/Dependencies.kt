@file:Suppress("unused")

private const val koin_version = "3.2.2"
private const val koin_android_version = "3.3.0"
private const val koin_ksp_version = "1.1.1"
private const val koin_ktor= "3.2.2"
private const val retrofit_version = "2.9.0"
private const val retrofitJson_version = "0.8.0"
private const val retrofitLogging_version = "4.10.0"
private const val ktxJsonSerializationVersion = "1.4.1"
private const val coreKTX_version = "1.8.0"
private const val appCompat_version = "1.5.1"
private const val material_version = "1.7.0"
private const val constraintLayout_version = "2.1.4"
private const val swipeRefreshLayout_version = "1.1.0"
private const val preferenceDataStore_version = "1.0.0"
private const val coil_version = "2.2.2"
private const val imageCompressor_version = "3.0.1"
private const val splashScreen_version = "1.0.0"
private const val gifDrawable_version = "1.2.25"
private const val paging_version = "3.1.1"
private const val googleMaps_version = "18.1.0"
private const val location_version = "21.0.1"


const val coroutineVersion = "1.6.4"

const val jUnitVersion = "4.13.2"
const val mockitoVersion = "4.9.0"
const val kotlinMockitoVersion = "4.1.0"
const val room_version = "2.4.3"

private const val androidJunit_version = "1.1.4"
private const val androidEkspresso_version = "3.5.1"

object Deps {
    // default dependency
    private const val coreKTX = "androidx.core:core-ktx:$coreKTX_version"
    private const val appCompat = "androidx.appcompat:appcompat:$appCompat_version"
    private const val material = "com.google.android.material:material:$material_version"
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayout_version"
    private const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshLayout_version"
    // ==============================  Splash  ===============================
    private const val splashScreen = "androidx.core:core-splashscreen:$splashScreen_version"
    // ===============================  Maps  ================================
    private const val gmaps = "com.google.android.gms:play-services-maps:$googleMaps_version"
    // =============================  Location  ==============================
    private const val glocation = "com.google.android.gms:play-services-location:$location_version"
    // ===============================  Koin  ================================
    private const val koinCore = "io.insert-koin:koin-core:$koin_version"
    private const val koinAndroid = "io.insert-koin:koin-android:$koin_android_version"
    private const val koinAnnotation = "io.insert-koin:koin-annotations:$koin_ksp_version"
    // ========================= Jetpack WorkManager =========================
    private const val koinWorkmanager = "io.insert-koin:koin-androidx-workmanager:$koin_android_version"
    // ============================ Koin for Ktor ============================
    private const val koinKtor = "io.insert-koin:koin-ktor:$koin_ktor"
    // ============================== Retrofit ===============================
    private const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
    private const val retrofitKTXSerializationJson = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$retrofitJson_version"
    private const val retrofitLogging = "com.squareup.okhttp3:logging-interceptor:$retrofitLogging_version"
    // ======================== Kotlinx Serialization ========================
    private const val ktxSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:$ktxJsonSerializationVersion"
    // ======================== Preferences DataStore ========================
    private const val prefDataStore = "androidx.datastore:datastore-preferences:$preferenceDataStore_version"
    // ================================ Coil =================================
    private const val coil = "io.coil-kt:coil:$coil_version"
    // ========================== Image Compressor ===========================
    private const val imageCompressor = "id.zelory:compressor:$imageCompressor_version"
    // ==========================    Gif Player    ===========================
    private const val gifDrawable = "pl.droidsonroids.gif:android-gif-drawable:$gifDrawable_version"
    // ==========================       Paging     ===========================
    private const val paging = "androidx.paging:paging-runtime-ktx:$paging_version"
    // ========================    Room     ========================
    private const val room = "androidx.room:room-runtime:$room_version"
    private const val roomExtension = "androidx.room:room-ktx:$room_version"
    private const val roomPaging = "androidx.room:room-paging:$room_version"
    // ======================   Espresso    =======================
    private const val espressoIdlingResource = "androidx.test.espresso:espresso-idling-resource:$androidEkspresso_version"
}

object KSP {
    // ========================    Room     ========================
    private const val room = "androidx.room:room-compiler:$room_version"
    // ========================    Koin     ========================
    private const val koin = "io.insert-koin:koin-ksp-compiler:$koin_ksp_version"
}

object DepsTest {
    // =========================   Core    =========================
    private const val jUnit = "junit:junit:$jUnitVersion"
    private const val coreTesting = "androidx.arch.core:core-testing:2.1.0"
    // ========================  Coroutine  ========================
    private const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion"
    // ========================    Koin     ========================
    private const val koinTest = "io.insert-koin:koin-test:$koin_version"
    private const val koinJunit4 = "io.insert-koin:koin-test-junit4:$koin_version"
    // ========================    Mock     ========================
    private const val mock = "org.mockito:mockito-core:$mockitoVersion"
    private const val mockInline = "org.mockito:mockito-inline:$mockitoVersion"
    private const val kotlinMock = "org.mockito.kotlin:mockito-kotlin:$kotlinMockitoVersion"
    // ========================    Room     ========================
    private const val room = "androidx.room:room-testing:$room_version"
}

object DepsAndroidTest {
    // =========================   Core    =========================
    private const val junit = "androidx.test.ext:junit:$androidJunit_version"
    // ======================   Espresso    =======================
    private const val espresso = "androidx.test.espresso:espresso-core:$androidEkspresso_version"
    private const val espressoIntent = "androidx.test.espresso:espresso-intents:$androidEkspresso_version"
    private const val espressoContrib = "androidx.test.espresso:espresso-contrib:$androidEkspresso_version"
    // ========================  Coroutine  ========================
    private const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion"
}