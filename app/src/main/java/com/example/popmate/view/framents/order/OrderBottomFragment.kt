package com.example.popmate.view.framents.order

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.popmate.R
import com.example.popmate.model.data.remote.order.StoreItem
import com.example.popmate.view.activities.order.OrderDetailActivity


class OrderBottomFragment : Fragment(){
    private lateinit var price: TextView
    private lateinit var cnt: TextView
    var index = 0
    var totalPrice =0
    var totalCnt = 0
    val hashMap = HashMap<Int, Int>()
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
            startActivity(intent)
        }
        return view
    }

    fun update(value: StoreItem) {
        index = value.tbItemId
        if(hashMap.containsKey(index)){
            totalCnt -= 1
            totalPrice -= hashMap[index] ?: 0
            hashMap.remove(index)
        }else{
            hashMap.put(index,value.price)
            totalCnt += 1
            totalPrice += value.price
        }


        price.text = totalPrice.toString()
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
