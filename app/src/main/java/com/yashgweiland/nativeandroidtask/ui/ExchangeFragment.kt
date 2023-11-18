package com.yashgweiland.nativeandroidtask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yashgweiland.nativeandroidtask.R
import com.yashgweiland.nativeandroidtask.common.BaseFragment
import com.yashgweiland.nativeandroidtask.common.BaseViewModel
import com.yashgweiland.nativeandroidtask.data.ResultData
import com.yashgweiland.nativeandroidtask.data.model.Result
import com.yashgweiland.nativeandroidtask.databinding.FragmentExchangeBinding
import com.yashgweiland.nativeandroidtask.notifiers.Notify
import com.yashgweiland.nativeandroidtask.ui.activity.MainActivity
import com.yashgweiland.nativeandroidtask.ui.viewmodel.MainViewModel
import com.yashgweiland.nativeandroidtask.utils.Constant.Companion.ON_FAILURE
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExchangeFragment : BaseFragment() {

    private lateinit var mainActivity: MainActivity
    private val mainViewModel: MainViewModel by viewModel()
    private val binding: FragmentExchangeBinding by lazyBinding()
    override val dataBinding: Boolean = true
    override val layoutResource: Int = R.layout.fragment_exchange

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity
    }

    override fun getViewModel(): BaseViewModel {
        return mainViewModel
    }
    override fun onNotificationReceived(data: Notify) {

        when(data.identifier){
            MainViewModel.ON_LISTING_DATA_FETCH -> {
                val response = data.arguments[0] as ResultData
                println("hh yashal response is $response")
            }

            ON_FAILURE -> {
                val errorMessage = data.arguments[0] as String
                println("hh yashal response is $errorMessage")
            }
        }

    }
    override fun setBindings() {
        binding.viewModel = mainViewModel
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTitleBar(requireContext().resources.getString(R.string.exchange))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getCryptoListApi(requireContext())
    }
}