package com.yashgweiland.nativeandroidtask.ui.viewmodel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.yashgweiland.nativeandroidtask.common.BaseViewModel
import com.yashgweiland.nativeandroidtask.data.model.FilterOptions
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
    var isShowView = ObservableField(false)
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

    fun getCryptoInfoApi(context: Context, slug: String){
        if (checkInternet()) {
            viewModelScope.launch {
                try {
                    val response = fetchJokeUseCase.getCryptoInfo(slug)
                    response.let {
                        when(it) {
                            is Result.Success -> {
                                notifier.notify(Notify(ON_INFO_DATA_FETCH, it.data))
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

    fun onViewClick(){
        notifier.notify(Notify(VIEW_ALL_CLICK, ""))
    }
    fun onFilterClick(){
        notifier.notify(Notify(FILTER_CLICK, ""))
    }
    fun onFilterOptionClick(filterItem: FilterOptions, position: Int){
        notifier.notify(Notify(FILTER_OPTION_CLICK, filterItem, position))
    }
    companion object {
        const val ON_LISTING_DATA_FETCH = "ON_LISTING_DATA_FETCH"
        const val VIEW_ALL_CLICK = "VIEW_ALL_CLICK"
        const val FILTER_CLICK = "FILTER_CLICK"
        const val FILTER_OPTION_CLICK = "FILTER_OPTION_CLICK"
        const val ON_INFO_DATA_FETCH = "ON_INFO_DATA_FETCH"
    }
}