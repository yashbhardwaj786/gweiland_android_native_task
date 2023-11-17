plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
}

android {
    namespace = "com.yashgweiland.nativeandroidtask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yashgweiland.nativeandroidtask"
        minSdk = 24
        targetSdk = 34
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
    dexOptions {
        incremental = true
        preDexLibraries = false
        javaMaxHeapSize = "4g"
    }
    dataBinding.enable = true
    flavorDimensions.add("tier")
    productFlavors {
        create("prod")  {
            applicationId = "com.yashgweiland.nativeandroidtask"
            minSdk  = 24
            targetSdk  = 34
            this.resValue("string", "app_name", providers.gradleProperty("app_name").get())
            this.buildConfigField("String","baseUrl", providers.gradleProperty("base_url").get())
            this.buildConfigField("String","apiKey", providers.gradleProperty("api_key").get())
            dimension = "tier"
        }
    }
}

dependencies {

    implementation(Dependencies.kotlin)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material_design)
    implementation(Dependencies.Navigation.fragment_ktx)
    implementation(Dependencies.Navigation.ui_ktx)
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