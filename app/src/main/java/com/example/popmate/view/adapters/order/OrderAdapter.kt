package com.example.popmate.view.adapters.order



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popmate.databinding.ListOrderItemBinding
import com.example.popmate.model.data.remote.order.PopupStoreItem
import java.text.NumberFormat
import java.util.Locale

interface OnItemClick {
    fun onClick(value: PopupStoreItem, orderGoodsCheck: View)
}

class OrderAdapter(private val itemClickCallback: OnItemClick):RecyclerView.Adapter<OrderAdapter.Holder>() {

    var listData = mutableListOf<PopupStoreItem>()
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
                val storeItem: PopupStoreItem = listData[curPos]
                itemClickCallback.onClick(storeItem,binding.orderGoodsCheck)
                //binding.orderGoodsImg.alpha = 0.2f
            }
        }
        fun setGoods(item: PopupStoreItem) {
            binding.orderGoodsName.text = item.name
            val amount = item.amount
            val totalAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(amount)
            binding.orderGoodsPrice.text = totalAmount
            // Glide를 사용하여 이미지 로드 및 표시
            Glide.with(binding.root.context) // Glide를 현재 컨텍스트에 연결
                .load(item.imgUrl) // 이미지 URL 설정 (item.imgUrl은 이미지의 URL입니다)
                .into(binding.orderGoodsImg) // 이미지를 표시할 ImageView 설정
        }
    }
}

