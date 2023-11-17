plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
}

android {
    namespace = "com.yashgweiland.nativeandroidtask"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.yashgweiland.nativeandroidtask"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding.enable = true
}

dependencies {

    implementation(Dependencies.kotlin)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material_design)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.espresso_core)
    implementation(Dependencies.viewModel)
    implementation(Dependencies.fragment_ktx)
    kapt(Dependencies.coroutine)
    implementation (Dependencies.Koin.koin)
    implementation (Dependencies.Retrofit.retrofit)
    implementation (Dependencies.Retrofit.logging_interceptor)
    implementation (Dependencies.Retrofit.moshi)
    implementation (Dependencies.Retrofit.main)
    implementation (Dependencies.timber)
}