package com.example.popmate.view.activities.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.popmate.R
import com.example.popmate.model.data.remote.order.StoreItem


class OrderBottomFragment : Fragment(){
    private lateinit var price: TextView
    private lateinit var cnt: TextView
    var totalPrice =0
    var totalCnt = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_bottom, container, false)
        price = view.findViewById(R.id.order_total_price)
        cnt = view.findViewById(R.id.order_cnt)
//        view.setOnClickListener {
//            val intent = Intent(requireContext(), OtherActivity::class.java)
//            startActivity(intent)
//        }
        return view
    }

    fun update(value: StoreItem) {
        Log.d("popmate", totalPrice.toString())
        Log.d("popmate", totalCnt.toString())
        totalPrice += value.price
        totalCnt += 1
        price.text = value.price.toString()
        cnt.text = value.price.toString()
    }
}