package com.example.popmate.view.adapters.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ItemPreReservationBinding
import com.example.popmate.model.data.remote.reservation.MyReservationRequest

class PreReservationAdapter(
    private val itemList: List<MyReservationRequest>
) : RecyclerView.Adapter<PreReservationAdapter.PreReservationViewHolder>() {

    inner class PreReservationViewHolder(binding: ItemPreReservationBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvTitle
        val tvStartTime = binding.tvStartTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreReservationViewHolder {
        val binding = ItemPreReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreReservationViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvTitle.text = item.popupStoreTitle
        holder.tvStartTime.text = item.startTime
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
