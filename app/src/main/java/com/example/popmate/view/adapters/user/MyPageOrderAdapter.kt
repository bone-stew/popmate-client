package com.example.popmate.view.adapters.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ListMypagePurchaseItemBinding
import com.example.popmate.model.data.remote.order.OrderItem

class MyPageOrderAdapter(): RecyclerView.Adapter<MypageOrderHolder>() {
    private var onItemClickListener: ((OrderItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (OrderItem) -> Unit) {
        onItemClickListener = listener
    }
    var listData = mutableListOf<OrderItem>()
    private var currentFragment: Fragment? = null // 현재 표시된 프래그먼트를 저장하는 변수

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MypageOrderHolder {
        val binding = ListMypagePurchaseItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)

        val holder = MypageOrderHolder(binding)

        binding.btnMypagePurchaseDetail.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val clickedItem = listData[position]
                onItemClickListener?.invoke(clickedItem) // 클릭 이벤트 핸들러 호출
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: MypageOrderHolder, position: Int) {
        val goods = listData.get(position)
        holder.setItems(goods)
    }
}

class MypageOrderHolder(val binding: ListMypagePurchaseItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun setItems(item: OrderItem){
        binding.txtMypageOrderDetailStoreName.text = item.storeId.toString()
        binding.txtMypageOrderDetailStoreName.text = item.orderId.toString()
    }
}