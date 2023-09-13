package com.example.popmate.view.adapters.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popmate.databinding.ListMypagePurchaseItemBinding
import com.example.popmate.model.data.remote.user.Orders
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MyPageOrderAdapter(): RecyclerView.Adapter<MypageOrderHolder>() {
    private var onItemClickListener: ((Orders) -> Unit)? = null
    private var onImgClickListener : ((Long) -> Unit)? = null
    fun setOnItemClickListener(listener: (Orders) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnImgClickListener(listener: (Long) -> Unit) {
        onImgClickListener = listener
    }
    var listData = mutableListOf<Orders>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MypageOrderHolder {
        val binding = ListMypagePurchaseItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)

        val holder = MypageOrderHolder(binding)

        binding.imgListMypageBannerimg.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val popupStoreId = listData[position].popupStoreId.toLong()
                onImgClickListener?.invoke(popupStoreId)
            }
            val popupStoreId  = listData[position].popupStoreId.toLong()

        }

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
    fun setItems(item: Orders){
        binding.txtMypageOrderDetailStoreName.text = item.title
        val koreanLocale = Locale("ko", "KR")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", koreanLocale)
        val date = inputFormat.parse(item.createdAt)
        val outputFormat = SimpleDateFormat("M.d (EEE) ", koreanLocale)
        val timeFormat = SimpleDateFormat("HH:mm", koreanLocale)

        val formattedTime = timeFormat.format(date)

        binding.txtListMypagePurchaseDay.text =  outputFormat.format(date)
        binding.txtListMypagePurchasePickupDay.text = outputFormat.format(date)
        binding.txtListMypagePurchasePickupTime.text = "$formattedTime ~"
        if(item.orderItemList.size == 1){
            binding.txtMypageOrderDetailStoreItems.text = item.orderItemList[0].popupStoreItem.name
        }else{
            binding.txtMypageOrderDetailStoreItems.text = item.orderItemList[0].popupStoreItem.name + " 외 ${item.orderItemList.size-1}개"
        }
        if(item.status == 0){
            binding.txtListMypagePurchasePickup.text = "픽업대기"
        }else if(item.status == 1){
            binding.txtListMypagePurchasePickup.text = "픽업완료"
        }else{
            binding.txtListMypagePurchasePickup.text = "주문취소"
        }
        Glide.with(binding.root.context)
            .load(item.bannerImgUrl)
            .into(binding.imgListMypageBannerimg)
    }
}