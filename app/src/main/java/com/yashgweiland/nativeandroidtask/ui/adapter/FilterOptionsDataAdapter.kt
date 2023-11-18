package com.yashgweiland.nativeandroidtask.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yashgweiland.nativeandroidtask.R
import com.yashgweiland.nativeandroidtask.data.MyData
import com.yashgweiland.nativeandroidtask.data.model.FilterOptions
import com.yashgweiland.nativeandroidtask.databinding.LayoutFilterItemBinding
import com.yashgweiland.nativeandroidtask.ui.viewmodel.MainViewModel

class FilterOptionsDataAdapter(
    private val mainViewModel: MainViewModel,
    var filterList: ArrayList<FilterOptions>,

) : RecyclerView.Adapter<FilterOptionsDataAdapter.FilterOptionsViewHolder>()  {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterOptionsViewHolder {
        context = parent.context
        val binding: LayoutFilterItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_filter_item, parent, false)
        return FilterOptionsViewHolder(binding.root, binding)
    }

    override fun getItemCount() = filterList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FilterOptionsViewHolder, position: Int) {
        val filterItemData = filterList[position]
        holder.binding.viewModel = mainViewModel
        holder.binding.data = filterItemData
        holder.binding.position = holder.adapterPosition
    }

    class FilterOptionsViewHolder(itemView: View, var binding: LayoutFilterItemBinding) :
        RecyclerView.ViewHolder(itemView)
}