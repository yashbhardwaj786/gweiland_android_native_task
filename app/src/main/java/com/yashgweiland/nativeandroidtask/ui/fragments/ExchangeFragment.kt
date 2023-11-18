package com.yashgweiland.nativeandroidtask.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.internal.LinkedTreeMap
import com.yashgweiland.nativeandroidtask.R
import com.yashgweiland.nativeandroidtask.common.BaseFragment
import com.yashgweiland.nativeandroidtask.common.BaseViewModel
import com.yashgweiland.nativeandroidtask.data.CryptoDataResponse
import com.yashgweiland.nativeandroidtask.data.CryptoInfoResponse
import com.yashgweiland.nativeandroidtask.data.MyData
import com.yashgweiland.nativeandroidtask.data.ResultData
import com.yashgweiland.nativeandroidtask.data.model.FilterOptions
import com.yashgweiland.nativeandroidtask.databinding.DialogFilerOptionsBinding
import com.yashgweiland.nativeandroidtask.databinding.FragmentExchangeBinding
import com.yashgweiland.nativeandroidtask.notifiers.Notify
import com.yashgweiland.nativeandroidtask.ui.activity.MainActivity
import com.yashgweiland.nativeandroidtask.ui.adapter.CryptoListDataAdapter
import com.yashgweiland.nativeandroidtask.ui.adapter.FilterOptionsDataAdapter
import com.yashgweiland.nativeandroidtask.ui.viewmodel.MainViewModel
import com.yashgweiland.nativeandroidtask.ui.viewmodel.MainViewModel.Companion.FILTER_CLICK
import com.yashgweiland.nativeandroidtask.ui.viewmodel.MainViewModel.Companion.ON_INFO_DATA_FETCH
import com.yashgweiland.nativeandroidtask.utils.Constant.Companion.ON_FAILURE
import com.yashgweiland.nativeandroidtask.utils.Constant.Companion.ON_STARTED
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExchangeFragment : BaseFragment() {

    private lateinit var mainActivity: MainActivity
    private val mainViewModel: MainViewModel by viewModel()
    private val binding: FragmentExchangeBinding by lazyBinding()
    override val dataBinding: Boolean = true
    private var mLayoutManager: LinearLayoutManager? = null
    private var cryptoListFull = ArrayList<CryptoDataResponse>()
    private var searchList = ArrayList<CryptoDataResponse>()
    private var partialList = ArrayList<CryptoDataResponse>()
    override val layoutResource: Int = R.layout.fragment_exchange
    private var isViewAllClick = false
    private var dialog: BottomSheetDialog? = null
    private var recyclerViewList: RecyclerView? = null
    private val filterList = ArrayList<FilterOptions>()
    private val myDataArrList = HashMap<Int, MyData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = context as MainActivity
    }

    override fun getViewModel(): BaseViewModel {
        return mainViewModel
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onNotificationReceived(data: Notify) {

        when(data.identifier){
            MainViewModel.VIEW_ALL_CLICK -> {
                binding.viewAll.visibility = View.GONE
                showAllData()
            }
            MainViewModel.ON_LISTING_DATA_FETCH -> {
                val response = data.arguments[0] as ResultData
                binding.progressAnimationView.visibility = View.GONE
                binding.viewAll.visibility = View.VISIBLE
                response.data?.let {
                    if(it.isNotEmpty()){
                        cryptoListFull.clear()
                        partialList.clear()
                        cryptoListFull = it
                         partialList = ArrayList<CryptoDataResponse>()
                        val size = if(cryptoListFull.size < 8){
                            cryptoListFull.size
                        }else {
                            7
                        }
                        for (item in 0..size){
                            partialList.add(cryptoListFull[item])
                        }
                        binding.data = it[0]
                        mainViewModel.isShowView.set(true)
                        it[0].quote?.usd?.price?.let { price->
                            binding.priceValue = String.format("%.2f", price)
                        }
                        it[0].quote?.usd?.percentChange?.let { changes->
                            if(changes < 0){
                                binding.changes.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                                binding.changeValue = String.format("%.2f", changes)
                            } else {
                                binding.changes.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                                binding.changeValue = "+"+String.format("%.2f", changes)
                            }

                        }
                        binding.isShowPadding = true
                        binding.cryptoList.visibility = View.VISIBLE
                        val resultString = cryptoListFull.joinToString(separator = ",") { item->  item.slug.toString() }
                        mainViewModel.getCryptoInfoApi(requireContext(), resultString)

                    }else {
                        binding.cryptoList.visibility = View.GONE
                    }
                }
            }

            ON_STARTED -> {
                binding.progressAnimationView.visibility = View.VISIBLE
                mainViewModel.isShowView.set(false)
                binding.cryptoList.visibility = View.GONE
            }

            ON_FAILURE -> {
                val errorMessage = data.arguments[0] as String
                binding.progressAnimationView.visibility = View.GONE
                mainViewModel.isShowView.set(false)
                binding.cryptoList.visibility = View.GONE
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }

            MainViewModel.ON_FAILURE_INFO -> {
                val errorMessage = data.arguments[0] as String
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }

            MainViewModel.FILTER_OPTION_CLICK -> {
                val options = data.arguments[0] as FilterOptions
                val pos = data.arguments[1] as Int
                for (item in filterList.indices){
                    filterList[item].isSelected = item == pos
                }
                (recyclerViewList?.adapter as FilterOptionsDataAdapter).filterList =
                    filterList
                recyclerViewList?.adapter?.notifyDataSetChanged()
                dialog?.dismiss()
                mainViewModel.getCryptoListApi(requireContext(), sort = options.value, sortBy = options.sort)

            }

            FILTER_CLICK -> {
                if (filterList.isNotEmpty())
                    openChooserDialogBottomSheet(filterList)
            }

            ON_INFO_DATA_FETCH -> {
                val response = data.arguments[0] as CryptoInfoResponse
                var outerHashMap: LinkedTreeMap<*, *> = LinkedTreeMap<String, String>()
                var innerHashMap: LinkedTreeMap<*, *> = LinkedTreeMap<String, String>()
                try {
                    outerHashMap = response.data as LinkedTreeMap<*, *>

                }catch (ex: Exception){
                    println(ex.printStackTrace())
                }


                outerHashMap.forEach{ (key, value) ->
                    innerHashMap = value as LinkedTreeMap<*, *>
                    innerHashMap.forEach { (key1, value1) ->
                        val id = innerHashMap["id"] as Double
                        val name = innerHashMap["name"] as String
                        val symbol = innerHashMap["symbol"] as String
                        val logo = innerHashMap["logo"] as String
                        val myData = MyData(id, name, symbol, logo)
                        myDataArrList[id.toInt()] = myData
                        initRecyclerView(partialList)
                    }
                }
                myDataArrList.forEach { (key, value) ->
                    if (key == partialList[0].id){
                        binding.imageUrl = myDataArrList[key]?.logo
                    }
                }
            }
        }

    }
    fun <K, V> LinkedTreeMap<K, V>.toHashMap(): HashMap<K, V> {
        val hashMap = HashMap<K, V>()
        for ((key, value) in this) {
            hashMap[key] = value
        }
        return hashMap
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

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
            }

            override fun afterTextChanged(arg0: Editable) {
                val queryString = binding.searchView.text?.toString()?.trim() ?: ""
                val tempList = if (isViewAllClick) cryptoListFull else partialList
                if (queryString.length > 2) {
                    binding.viewAll.visibility = View.GONE
                    searchList.clear()
                    val list = tempList.filter { it.name!!.contains(queryString, ignoreCase = true)
                            || it.symbol!!.contains(queryString, ignoreCase = true) }
                    searchList = ArrayList(list)
                    binding.isShowPadding = false
                    initRecyclerView(searchList)
                } else if (queryString.isEmpty()) {
                    binding.viewAll.visibility = View.VISIBLE
                    searchList.clear()
                    showAllData()
                }
            }
        })

        val filterOption = FilterOptions().apply {
            name = "Price - Low to High"
            value = "price"
            sort = "asc"
        }
        val filterOption1 = FilterOptions().apply {
            name = "Price - High to Low"
            value = "price"
            sort = "desc"
        }

        val filterOption2 = FilterOptions().apply {
            name = "Volume 24 - Low to High"
            value = "volume_24h"
            sort = "asc"
        }

        val filterOption3 = FilterOptions().apply {
            name = "Volume 24 - High to Low"
            value = "volume_24h"
            sort = "desc"
        }
        filterList.add(filterOption)
        filterList.add(filterOption1)
        filterList.add(filterOption2)
        filterList.add(filterOption3)

    }

    private fun initRecyclerView(cryptoList: ArrayList<CryptoDataResponse>) {
        binding.cryptoList.apply {
            mLayoutManager = LinearLayoutManager(context)
            layoutManager = mLayoutManager
            adapter =
                CryptoListDataAdapter(mainViewModel, cryptoList, myDataArrList)
        }
    }

    private fun openChooserDialogBottomSheet(filterList: ArrayList<FilterOptions>) {
        val inflater = layoutInflater
        val dialogImageChooserBinding = inflater?.let {
            DataBindingUtil.inflate<DialogFilerOptionsBinding>(
                it,
                R.layout.dialog_filer_options, null, false
            )
        }?.apply {
            viewModel = mainViewModel
            executePendingBindings()
        }

        dialog =
            dialog ?: BottomSheetDialog(requireContext(), R.style.TransParentBottomSheetDialog).apply {
                dialogImageChooserBinding?.root?.let { setContentView(it) }
            }
        recyclerViewList = dialog?.findViewById(R.id.filterList)
        recyclerViewList?.let {
            initFilterRecyclerView(filterList, it)
        }


        dialog?.show()
    }

    private fun initFilterRecyclerView(filterList: ArrayList<FilterOptions>,recyclerViewList: RecyclerView ) {
        recyclerViewList.apply {
            mLayoutManager = LinearLayoutManager(context)
            layoutManager = mLayoutManager
            adapter =
                FilterOptionsDataAdapter(mainViewModel, filterList)
        }
    }

    private fun showAllData(){
        if (cryptoListFull.isNotEmpty()){
            isViewAllClick = true
            initRecyclerView(cryptoListFull)
            binding.isShowPadding = true
        }
    }


}