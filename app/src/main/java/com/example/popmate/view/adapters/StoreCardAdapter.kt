package com.example.popmate.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.StoreCardItemBinding
import com.example.popmate.model.data.local.PopupStore

class StoreCardAdapter(private val stores: List<PopupStore>) :
    RecyclerView.Adapter<StoreCardAdapter.StoreCardViewHolder>() {

    inner class StoreCardViewHolder(private val binding: StoreCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(store: PopupStore) {
                binding.run {
                    storePlace.text = store.departmentName
                    storeTitle.text = store.title
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreCardViewHolder {
        val binding = StoreCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StoreCardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return stores.size
    }

    override fun onBindViewHolder(holder: StoreCardViewHolder, position: Int) {
        holder.bind(stores[position])
    }
}