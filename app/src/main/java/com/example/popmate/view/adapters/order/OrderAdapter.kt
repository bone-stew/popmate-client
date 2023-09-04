package com.example.popmate.view.adapters.order



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ListOrderItemBinding
import com.example.popmate.model.data.remote.order.StoreItem

interface OnItemClick {
    fun onClick(value: StoreItem)
}

class OrderAdapter(private val itemClickCallback: OnItemClick):RecyclerView.Adapter<OrderAdapter.Holder>() {

    var listData = mutableListOf<StoreItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListOrderItemBinding.inflate(LayoutInflater.from(parent.context),
        parent,false)

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val goods = listData.get(position)
        holder.setGoods(goods)
    }

    inner class Holder(val binding: ListOrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.orderItem.setOnClickListener {
                val curPos: Int = bindingAdapterPosition
                val storeItem: StoreItem = listData[curPos]
                itemClickCallback.onClick(storeItem)
            }
        }

        fun setGoods(item: StoreItem) {
            binding.orderGoodsName.text = item.name
            binding.orderGoodsPrice.text = item.price.toString()
        }
    }
}

