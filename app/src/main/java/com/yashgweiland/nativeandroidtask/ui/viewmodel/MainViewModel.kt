package com.yashgweiland.nativeandroidtask.ui.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.yashgweiland.nativeandroidtask.common.BaseViewModel
import com.yashgweiland.nativeandroidtask.data.model.Result
import com.yashgweiland.nativeandroidtask.domain.FetchJokeUseCase
import com.yashgweiland.nativeandroidtask.notifiers.Notify
import com.yashgweiland.nativeandroidtask.utils.Constant
import com.yashgweiland.nativeandroidtask.utils.Constant.Companion.DEFAULT_SORT_BY
import com.yashgweiland.nativeandroidtask.utils.Constant.Companion.DEFAULT_SORT_VALUE
import com.yashgweiland.nativeandroidtask.utils.Constant.Companion.LIMIT
import com.yashgweiland.nativeandroidtask.utils.Utility
import com.yashgweiland.nativeandroidtask.utils.checkInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class MainViewModel(
    private val fetchJokeUseCase: FetchJokeUseCase
): BaseViewModel() {

    fun getCryptoListApi(context: Context, limit: Int = LIMIT, sort: String = DEFAULT_SORT_VALUE, sortBy: String = DEFAULT_SORT_BY){
        notifier.notify(Notify(Constant.ON_STARTED, ""))
        if (checkInternet()) {
            viewModelScope.launch {
                try {
                    val response = fetchJokeUseCase.getLatestCryptoListing(limit, sort, sortBy)
                    response.let {
                        when(it) {
                            is Result.Success -> {
                                notifier.notify(Notify(ON_LISTING_DATA_FETCH, it.data))
                                return@launch
                            }
                            is Result.Error -> {
                                it.exception.printStackTrace()
                                notifier.notify(Notify(Constant.ON_FAILURE, it.exception.localizedMessage))
                            }
                        }
                    }
                }catch (ex: Exception) {
                    hideProgress()
                    ex.printStackTrace()
                    notifier.notify(Notify(Constant.ON_FAILURE, ex.localizedMessage))
                }
            }
        } else {
            notifier.notify(Notify(Constant.ON_FAILURE, Constant.INTERNET_ISSUE_DESCRIPTION))
        }
    }
    companion object {
        const val ON_LISTING_DATA_FETCH = "ON_LISTING_DATA_FETCH"
    }
}