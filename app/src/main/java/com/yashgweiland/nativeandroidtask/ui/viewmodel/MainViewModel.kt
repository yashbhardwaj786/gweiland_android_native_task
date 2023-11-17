package com.yashgweiland.nativeandroidtask.ui.viewmodel

import com.yashgweiland.nativeandroidtask.common.BaseViewModel
import com.yashgweiland.nativeandroidtask.domain.FetchJokeUseCase

class MainViewModel(
    private val fetchJokeUseCase: FetchJokeUseCase
): BaseViewModel() {
}