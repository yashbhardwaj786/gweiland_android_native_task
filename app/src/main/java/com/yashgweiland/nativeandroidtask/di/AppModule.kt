package com.yashgweiland.nativeandroidtask.di

import com.yashgweiland.nativeandroidtask.network.ApiService
import org.koin.dsl.module

val appModule = module {
    single { ApiService.create() }
}