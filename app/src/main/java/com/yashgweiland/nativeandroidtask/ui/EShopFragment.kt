package com.yashgweiland.nativeandroidtask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yashgweiland.nativeandroidtask.R
import com.yashgweiland.nativeandroidtask.common.BaseFragment
import com.yashgweiland.nativeandroidtask.common.BaseViewModel
import com.yashgweiland.nativeandroidtask.databinding.FragmentEShopBinding
import com.yashgweiland.nativeandroidtask.databinding.FragmentExchangeBinding
import com.yashgweiland.nativeandroidtask.notifiers.Notify
import com.yashgweiland.nativeandroidtask.ui.activity.MainActivity
import com.yashgweiland.nativeandroidtask.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EShopFragment: BaseFragment() {

    private lateinit var mainActivity: MainActivity
    private val mainViewModel: MainViewModel by viewModel()
    private val binding: FragmentEShopBinding by lazyBinding()
    override val dataBinding: Boolean = true
    override val layoutResource: Int = R.layout.fragment_e_shop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity
    }

    override fun getViewModel(): BaseViewModel {
        return mainViewModel
    }

    override fun onNotificationReceived(data: Notify) {

    }

    override fun setBindings() {
        binding.viewModel = mainViewModel
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTitleBar(requireContext().resources.getString(R.string.e_shops))
    }
}