package com.yashgweiland.nativeandroidtask.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.yashgweiland.nativeandroidtask.R
import com.yashgweiland.nativeandroidtask.common.BaseFragment
import com.yashgweiland.nativeandroidtask.common.BaseViewModel
import com.yashgweiland.nativeandroidtask.data.CryptoDataResponse
import com.yashgweiland.nativeandroidtask.data.ResultData
import com.yashgweiland.nativeandroidtask.databinding.FragmentExchangeBinding
import com.yashgweiland.nativeandroidtask.notifiers.Notify
import com.yashgweiland.nativeandroidtask.ui.activity.MainActivity
import com.yashgweiland.nativeandroidtask.ui.adapter.CryptoListDataAdapter
import com.yashgweiland.nativeandroidtask.ui.viewmodel.MainViewModel
import com.yashgweiland.nativeandroidtask.utils.Constant.Companion.ON_FAILURE
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExchangeFragment : BaseFragment() {

    private lateinit var mainActivity: MainActivity
    private val mainViewModel: MainViewModel by viewModel()
    private val binding: FragmentExchangeBinding by lazyBinding()
    override val dataBinding: Boolean = true
    private var mLayoutManager: LinearLayoutManager? = null
    private var cryptoListFull = ArrayList<CryptoDataResponse>()
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
            MainViewModel.VIEW_ALL_CLICK -> {
                if (cryptoListFull.isNotEmpty()){
                    initRecyclerView(cryptoListFull)
                    binding.viewAll.visibility = View.INVISIBLE
                }
            }
            MainViewModel.ON_LISTING_DATA_FETCH -> {
                val response = data.arguments[0] as ResultData
                response.data?.let {
                    if(it.isNotEmpty()){
                        cryptoListFull = it
                        val partialList = ArrayList<CryptoDataResponse>()
                        var size = if(cryptoListFull.size < 8){
                            cryptoListFull.size
                        }else {
                            7
                        }
                        for (item in 0..size){
                            partialList.add(cryptoListFull[item])
                        }
                        binding.data = it[0]
                        it[0].quote?.USD?.price?.let { price->
                            binding.priceValue = String.format("%.2f", price)
                        }
                        it[0].quote?.USD?.percent_change_24h?.let { changes->
                            if(changes < 0){
                                binding.changes.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                                binding.changeValue = String.format("%.2f", changes)
                            } else {
                                binding.changes.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                                binding.changeValue = "+"+String.format("%.2f", changes)
                            }

                        }
                        initRecyclerView(partialList)
                    }
                }
            }

            ON_FAILURE -> {
                val errorMessage = data.arguments[0] as String
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

    private fun initRecyclerView(cryptoList: ArrayList<CryptoDataResponse>) {
        binding.cryptoList.apply {
            mLayoutManager = LinearLayoutManager(context)
            layoutManager = mLayoutManager
            adapter =
                CryptoListDataAdapter(mainViewModel, cryptoList)
        }
    }
}