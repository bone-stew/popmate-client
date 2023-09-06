package com.example.popmate.view.adapters.order



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ListOrderPurchaseItemBinding
import com.example.popmate.model.data.remote.order.PopupStoreItem


class OrderDetailAdapter():RecyclerView.Adapter<OrderDetailAdapter.orderDetailHolder>() {
    var listData = mutableListOf<PopupStoreItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orderDetailHolder {
        val binding = ListOrderPurchaseItemBinding.inflate(LayoutInflater.from(parent.context),
        parent,false)
        return orderDetailHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: OrderDetailAdapter.orderDetailHolder, position: Int) {
        val goods = listData.get(position)
        holder.setGoods(goods)
    }

    inner class orderDetailHolder(val binding: ListOrderPurchaseItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setGoods(item: PopupStoreItem) {
            binding.orderDetailName.text = item.name
            binding.orderDetailPrice.text = item.amount.toString()
        }
    }
}

