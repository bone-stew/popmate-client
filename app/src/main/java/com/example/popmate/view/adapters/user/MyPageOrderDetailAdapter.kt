package com.example.popmate.view.adapters.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ListMypagePurchaseDetailItemBinding
import com.example.popmate.model.data.remote.user.OrderListItem
import java.text.NumberFormat
import java.util.Locale


class MyPageOrderDetailAdapter() : RecyclerView.Adapter<MyPageOrderDetailHolder>() {
    var listData = mutableListOf<OrderListItem>()
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
    fun setItems(item: OrderListItem) {
        binding.orderDetailName.text = item.popupStoreItem!!.name
        val amount = item.popupStoreItem!!.amount
        val totalAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(amount)
        binding.orderDetailPrice.text = totalAmount
        binding.txtListMypagePurchaseDetailItemCnt.text = "${item.totalQuantity}개"
    }
}
