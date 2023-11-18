plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
//    id("com.google.devtools.ksp")
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
//    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")
//    implementation (Dependencies.Retrofit.moshi)
//    implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")
    implementation (Dependencies.Retrofit.main)
    implementation (Dependencies.timber)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}