package com.yashgweiland.nativeandroidtask.di

import com.yashgweiland.nativeandroidtask.data.repository.MainRepository
import org.koin.dsl.module

val repoModule = module{
    single { MainRepository(get()) }
}