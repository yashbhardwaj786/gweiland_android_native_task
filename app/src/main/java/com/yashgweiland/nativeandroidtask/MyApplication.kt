package com.yashgweiland.nativeandroidtask

import android.app.Application
import android.content.Context
import com.yashgweiland.nativeandroidtask.di.appModule
import com.yashgweiland.nativeandroidtask.di.domainModule
import com.yashgweiland.nativeandroidtask.di.repoModule
import com.yashgweiland.nativeandroidtask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

val allModule = listOf(
    appModule,
    repoModule,
    domainModule,
    viewModelModule
)

class MyApplication: Application() {
    companion object {
        private lateinit var application: MyApplication

        @JvmStatic
        fun getInstance(): MyApplication {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        startKoin{
            androidContext(this@MyApplication)
            modules(allModule)
        }
    }

    operator fun get(context: Context): MyApplication {
        return context.applicationContext as MyApplication
    }
}