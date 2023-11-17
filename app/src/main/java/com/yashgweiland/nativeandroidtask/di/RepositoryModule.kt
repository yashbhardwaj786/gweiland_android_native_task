package com.yashgweiland.nativeandroidtask.di

import com.yashgweiland.nativeandroidtask.data.preferences.JokesPreference
import com.yashgweiland.nativeandroidtask.data.repository.MainRepository
import com.yashgweiland.nativeandroidtask.data.repository.SharedPrefRepository
import org.koin.dsl.module

val repoModule = module{
    single { MainRepository(get()) }
    single { JokesPreference(get()) }
    single { SharedPrefRepository(get()) }
}