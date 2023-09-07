package com.example.popmate.view.adapters.order



import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ListOrderPurchaseItemBinding
import com.example.popmate.model.data.remote.order.PopupStoreItem


class OrderDetailAdapter(
    private val onAmountChanged: (position: Int, totalQuantity: Int, sign: String) -> Unit,
    private val onItemRemoved: (position: Int, item : PopupStoreItem) -> Unit
    ):RecyclerView.Adapter<OrderDetailAdapter.orderDetailHolder>() {

    var listData = mutableListOf<PopupStoreItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orderDetailHolder {
        val binding = ListOrderPurchaseItemBinding.inflate(LayoutInflater.from(parent.context),
        parent,false)
        return orderDetailHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: OrderDetailAdapter.orderDetailHolder, position: Int) {
        val goods = listData.get(position)

        holder.setGoods(goods)
    }

    inner class orderDetailHolder(val binding: ListOrderPurchaseItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setGoods(item: PopupStoreItem) {
            binding.orderDetailName.text = item.name
            binding.orderDetailPrice.text = item.amount.toString()
            binding.orderDetailListCnt.text = item.totalQuantity.toString()
            binding.orderDetailMinus.setOnClickListener {
                if(item.totalQuantity>1){
                    item.totalQuantity--
                    binding.orderDetailListCnt.text = item.totalQuantity.toString()
                    notifyItemChanged(bindingAdapterPosition)
                    onAmountChanged(bindingAdapterPosition, item.totalQuantity, "minus")
                }
            }

            binding.orderDetailPlus.setOnClickListener {

                if(item.totalQuantity<item.orderLimit){
                    item.totalQuantity++
                    binding.orderDetailListCnt.text = item.totalQuantity.toString()
                    notifyItemChanged(bindingAdapterPosition)
                    onAmountChanged(bindingAdapterPosition, item.totalQuantity, "plus")
                }
            }

            binding.orderDetailClose.setOnClickListener {
                val position = bindingAdapterPosition
                val item = listData.removeAt(position)
                Log.d("jjrr", listData.size.toString())
                notifyItemRemoved(position)
                onItemRemoved(position, item)
            }
        }
    }
}

