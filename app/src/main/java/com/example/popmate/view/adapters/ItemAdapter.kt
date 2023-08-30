package com.example.popmate.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ItemLayoutBinding
import com.example.popmate.model.data.local.Item




class ItemAdapter( private var items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var itemList: ArrayList<Item>? = null

    inner class ItemViewHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                Toast.makeText(binding.root.context, "클릭된 아이템 = ${binding.item!!.itemName}", Toast.LENGTH_LONG).show()
            }
        }
        fun setItem(item: Item){
            binding.item = item
        }
//        fun bind(recyclerViewItem: Item) = with(binding) {
//            item = recyclerViewItem
//        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items.get(position)
        item?.let { holder.setItem(it) }
    //        items.value?.get(position)?.let {
//            holder.bind(it)
//            println(it.itemName)
//        }
    }

    override fun getItemCount(): Int {
        return items.size!!
    }


}
