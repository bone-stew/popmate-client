package com.example.popmate.view.adapters.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ListMypagePurchaseDetailItemBinding
import com.example.popmate.model.data.remote.order.PopupStoreItem


class MyPageOrderDetailAdapter() : RecyclerView.Adapter<MyPageOrderDetailHolder>() {
    var listData = mutableListOf<PopupStoreItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageOrderDetailHolder {
        val binding = ListMypagePurchaseDetailItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)

        return MyPageOrderDetailHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: MyPageOrderDetailHolder, position: Int) {
        val goods = listData[position]
        holder.setItems(goods)
    }
}

class MyPageOrderDetailHolder(val binding: ListMypagePurchaseDetailItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setItems(item: PopupStoreItem) {
        binding.orderDetailName.text = item.name
        binding.orderDetailPrice.text = item.amount.toString()
    }
}
