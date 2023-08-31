package com.example.popmate.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.RowPopupstoreBinding
import com.example.popmate.model.data.local.PopupStore




class PopupStoreAdapter(private var popupStores: List<PopupStore>) : RecyclerView.Adapter<PopupStoreAdapter.PopupStoreViewHolder>() {

    inner class PopupStoreViewHolder(private val binding: RowPopupstoreBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                Toast.makeText(binding.root.context, "클릭된 아이템 = ${binding.popupstore!!.title}", Toast.LENGTH_LONG).show()
            }
        }
        fun setItem(popupStore: PopupStore){
            binding.popupstore = popupStore
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopupStoreViewHolder {
        val binding = RowPopupstoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopupStoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopupStoreViewHolder, position: Int) {
        val popupStore = popupStores.get(position)
        popupStore?.let { holder.setItem(it) }
    }

    override fun getItemCount(): Int {
        return popupStores.size!!
    }


}
