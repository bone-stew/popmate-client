package com.example.popmate.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ItemLayoutBinding
import com.example.popmate.model.data.local.Item




class ItemAdapter(private var items: LiveData<ArrayList<Item>>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var itemList: ArrayList<Item>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        items.value?.get(position)?.let {
            holder.bind(it)
            println(it.itemName)
        }
    }
    inner class ItemViewHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(recyclerViewItem: Item) = with(binding) {
            item = recyclerViewItem
        }

    }

    override fun getItemCount(): Int {
        return items.value?.size!!
    }


}
