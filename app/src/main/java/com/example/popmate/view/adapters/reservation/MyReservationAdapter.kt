package com.example.popmate.view.adapters.reservation

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popmate.databinding.ItemMyReservationBinding
import com.example.popmate.model.data.remote.reservation.MyReservationsResponse.MyReservationResponse
import com.example.popmate.util.DateTimeUtils
import com.example.popmate.view.activities.detail.PopupDetailActivity
import com.example.popmate.view.activities.reservation.MyReservationDetailActivity

class MyReservationAdapter(
    private val context: Context,
    private val itemList: List<MyReservationResponse>
) : RecyclerView.Adapter<MyReservationAdapter.PreReservationViewHolder>() {

    inner class PreReservationViewHolder(binding: ItemMyReservationBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvVisitStartDate = binding.tvVisitStartDate
        val tvVisitEndTime = binding.tvVisitEndTime
        val tvPopupStoreName = binding.tvPopupStoreName
        val imgPopupStore = binding.imgPopupStore
        val tvReservationStatus = binding.tvReservationStatus

        init {
            /**
             * 예약 상세 페이지로 이동
             */
            binding.layoutReservationDetail.setOnClickListener {
                val selectedUserReservationId: Long = itemList[adapterPosition].userReservationId
                val popupStoreId = Integer.parseInt(binding.tvId.text.toString())
                val intent = Intent(context, MyReservationDetailActivity::class.java)
                intent.putExtra("userReservationId", selectedUserReservationId)
                Log.d("MyReservationAdapter", "예약 상세 userReservationId: $selectedUserReservationId")
                context.startActivity(intent)
            }

            /**
             * 팝업 스토어 상세 페이지로 이동
             */
            binding.layoutPopupStore.setOnClickListener {
                val selectedPopupStoreId: Long = itemList[adapterPosition].popupStoreId
                val intent = Intent(context, PopupDetailActivity::class.java)
                intent.putExtra("id", selectedPopupStoreId)
                Log.d("MyReservationAdapter", "팝업 상세 popupStoreId: $selectedPopupStoreId")
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreReservationViewHolder {
        val binding = ItemMyReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreReservationViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvPopupStoreName.text = item.popupStoreTitle
        holder.tvReservationStatus.text = item.reservationStatus
        holder.tvVisitStartDate.text = DateTimeUtils().toMonthDayTimeString(item.startTime)
        holder.tvVisitEndTime.text = DateTimeUtils().toTimeString(item.endTime)
        Glide.with(context)
            .load(item.bannerImgUrl)
            .into(holder.imgPopupStore)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
