package com.yashgweiland.nativeandroidtask.di

import com.yashgweiland.nativeandroidtask.domain.FetchJokeUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        FetchJokeUseCase(get())
    }
}