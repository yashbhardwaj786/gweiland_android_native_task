object Versions {
    //************* Basic Dependencies ****************
    const val build_gradle = "7.2.2"
    const val kotlin_version = "1.8.0"
    const val core_ktx = "1.7.0"
    const val appcompat = "1.6.1"
    const val material_design = "1.8.0"
    const val constraintlayout = "2.1.4"
    const val espresso_core = "3.4.0"
    const val junit = "1.1.3"
    //************* Basic Dependencies ****************

    const val coroutine = "1.3.9"
    const val viewModel = "2.5.1"
    const val fragment_ktx = "1.5.5"
    const val timber = "4.7.1"
    const val koin = "3.4.3"
    const val retrofit = "2.9.0"
    const val okhttp = "4.2.0"
    const val nav_version = "2.7.5"
    const val gson_conertor_version = "2.9.0"
    const val lottie_version = "3.7.0"
}

object Dependencies {
    //************* Basic Dependencies ****************
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    const val build_gradle = "com.android.tools.build:gradle:${Versions.build_gradle}"
    const val build_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material_design = "com.google.android.material:material:${Versions.material_design}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val junit = "androidx.test.ext:junit:${Versions.junit}"
    //************* Basic Dependencies ****************

    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModel}"
    const val viewModel_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.viewModel}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie_version}"

    object Retrofit {
        const val retrofit = "io.insert-koin:koin-android:${Versions.retrofit}"
        const val main = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val logging_interceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
        const val gson_convertor = "com.squareup.retrofit2:converter-gson:${Versions.gson_conertor_version}"
    }

    object Navigation {
        const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
        const val ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"
    }

    object Koin {
        const val koin = "io.insert-koin:koin-android:${Versions.koin}"
    }
}