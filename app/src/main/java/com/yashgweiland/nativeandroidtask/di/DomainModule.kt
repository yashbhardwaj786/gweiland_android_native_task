package com.yashgweiland.nativeandroidtask.di

import com.yashgweiland.nativeandroidtask.domain.FetchCryptoUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        FetchCryptoUseCase(get())
    }
}