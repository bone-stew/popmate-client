package com.example.popmate.view.adapters.reservation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ItemMyReservationBinding
import com.example.popmate.model.data.remote.reservation.MyReservationsResponse.MyReservationResponse
import com.example.popmate.view.activities.reservation.MyReservationDetailActivity

class MyReservationAdapter(
    private val context: Context,
    private val itemList: List<MyReservationResponse>
) : RecyclerView.Adapter<MyReservationAdapter.PreReservationViewHolder>() {

    inner class PreReservationViewHolder(binding: ItemMyReservationBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvVisitStartDate = binding.tvVisitStartDate
        val tvPopupStoreName = binding.tvPopupStoreName
        val imgPopupStore = binding.imgPopupStore
        val tvReservationStatus = binding.tvReservationStatus

        init {
            /**
             * 예약 상세 페이지로 이동
             */
            binding.btnReservationDetail.setOnClickListener {
                val popupStoreId = Integer.parseInt(binding.tvId.text.toString())
                val intent = Intent(context, MyReservationDetailActivity::class.java)
                intent.putExtra("popupStoreId", popupStoreId)
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
        holder.tvVisitStartDate.text = item.startTime
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
