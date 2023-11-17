package com.yashgweiland.nativeandroidtask.di

import com.yashgweiland.nativeandroidtask.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}