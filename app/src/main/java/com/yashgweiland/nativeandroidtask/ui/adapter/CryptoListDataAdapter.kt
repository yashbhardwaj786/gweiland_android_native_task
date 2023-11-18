package com.yashgweiland.nativeandroidtask.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yashgweiland.nativeandroidtask.R
import com.yashgweiland.nativeandroidtask.data.CryptoDataResponse
import com.yashgweiland.nativeandroidtask.data.MyData
import com.yashgweiland.nativeandroidtask.databinding.LayoutCryptoItemBinding
import com.yashgweiland.nativeandroidtask.ui.viewmodel.MainViewModel

class CryptoListDataAdapter(
    private val mainViewModel: MainViewModel,
    private var cryptoList: ArrayList<CryptoDataResponse>,
    private val hashMap: HashMap<Int, MyData>,
) : RecyclerView.Adapter<CryptoListDataAdapter.RecentDownloadViewHolder>()  {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentDownloadViewHolder {
        context = parent.context
        val binding: LayoutCryptoItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_crypto_item, parent, false)
        return RecentDownloadViewHolder(binding.root, binding)
    }

    override fun getItemCount() = cryptoList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecentDownloadViewHolder, position: Int) {
        val cryptoItemData = cryptoList[position]
        holder.binding.viewModel = mainViewModel
        holder.binding.data = cryptoItemData
        cryptoItemData.quote?.usd?.price?.let { price->
            holder.binding.priceValue = String.format("%.2f", price)
        }
        cryptoItemData.quote?.usd?.percentChange?.let { changes->
            if(changes < 0){
                holder.binding.image.setBackgroundResource(R.drawable.decrement)
                holder.binding.changes.setTextColor(ContextCompat.getColor(context, R.color.red))
                holder.binding.changeValue = String.format("%.2f", changes)
            } else {
                holder.binding.image.setBackgroundResource(R.drawable.increment)
                holder.binding.changes.setTextColor(ContextCompat.getColor(context, R.color.green))
                holder.binding.changeValue = "+"+String.format("%.2f", changes)
            }
        }
        hashMap.forEach { (key, value) ->
            if (key == cryptoItemData.id){
                holder.binding.imageUrl = hashMap[key]?.logo
            }
        }
    }

    class RecentDownloadViewHolder(itemView: View, var binding: LayoutCryptoItemBinding) :
        RecyclerView.ViewHolder(itemView)
}