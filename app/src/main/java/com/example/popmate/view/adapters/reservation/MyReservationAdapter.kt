package com.example.popmate.view.adapters.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ItemMyReservationBinding
import com.example.popmate.model.data.remote.reservation.MyReservationRequest

class MyReservationAdapter(
    private val itemList: List<MyReservationRequest>
) : RecyclerView.Adapter<MyReservationAdapter.PreReservationViewHolder>() {

    inner class PreReservationViewHolder(binding: ItemMyReservationBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvVisitStartDate = binding.tvVisitStartDate
        val tvPopupStoreName = binding.tvPopupStoreName
        val imgPopupStore = binding.imgPopupStore
        val tvReservationStatus = binding.tvReservationStatus
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
