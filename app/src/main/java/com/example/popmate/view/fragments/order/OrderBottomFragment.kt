package com.example.popmate.view.fragments.order

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.popmate.R
import com.example.popmate.model.data.remote.order.OrderPlaceDetailResponse
import com.example.popmate.model.data.remote.order.PopupStoreItem
import com.example.popmate.view.activities.order.OrderDetailActivity
import java.text.NumberFormat
import java.util.Locale


class OrderBottomFragment(private val placedetail: OrderPlaceDetailResponse?) : Fragment(){
    private lateinit var price: TextView
    private lateinit var cnt: TextView
    var index = 0
    var totalPrice =0
    var totalCnt = 0
    val hashMap = HashMap<Int, PopupStoreItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_bottom, container, false)
        price = view.findViewById(R.id.order_total_price)
        cnt = view.findViewById(R.id.order_cnt)
        view.setOnClickListener {
            val intent = Intent(requireContext(), OrderDetailActivity::class.java)
            intent.putExtra("data",hashMap)
            Log.d("jjra","여기는 ${placedetail.toString()}")
            intent.putExtra("placeDetail",placedetail)
            startActivity(intent)
        }
        return view
    }

    fun update(value: PopupStoreItem) {
        index = value.itemId.toInt()
        if(hashMap.containsKey(index)){
            totalCnt -= 1
            totalPrice -= hashMap[index]?.amount ?: 0
            hashMap.remove(index)
        }else{
            hashMap.put(index,value)
            totalCnt += 1
            totalPrice += value.amount
        }

        val totalAmount = NumberFormat.getNumberInstance(Locale.KOREA).format(totalPrice)
        price.text = totalAmount
        cnt.text = totalCnt.toString()

        if (totalCnt == 0) {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().hide(this).commit()
        } else {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().show(this).commit()
        }
    }
}
