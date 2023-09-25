package com.example.popmate.view.adapters.order



import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.databinding.ListOrderPurchaseItemBinding
import com.example.popmate.model.data.remote.order.OrderPlaceDetailResponse
import com.example.popmate.model.data.remote.order.PopupStoreItem
import com.example.popmate.view.activities.order.OrderDetailActivity
import java.text.NumberFormat
import java.util.Locale


class OrderDetailAdapter(
    private val context : OrderDetailActivity,
    placeDetail: OrderPlaceDetailResponse,
    private val onAmountChanged: (position: Int, totalQuantity: Int, sign: String) -> Unit,
    private val onItemRemoved: (position: Int, item: PopupStoreItem) -> Unit
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
            val amount = item.amount
            val totalAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(amount)
            binding.orderDetailPrice.text = totalAmount
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
                }else if(item.totalQuantity==item.orderLimit){
                    Toast.makeText(context, "주문 가능한 최대 수량입니다", Toast.LENGTH_SHORT).show()
                }
            }

            binding.orderDetailClose.setOnClickListener {
                val position = bindingAdapterPosition
                val item = listData.removeAt(position)
                notifyItemRemoved(position)
                onItemRemoved(position, item)
            }
        }
    }
}

