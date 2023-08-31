package com.example.popmate.view.framents.order

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.FragmentOrderDetailBinding
import com.example.popmate.model.data.remote.order.StoreItem
import com.example.popmate.view.adapters.order.OrderDetailAdapter
import java.util.Date


class OrderDetailFragment : Fragment() {

    private lateinit var totalCnt : TextView
    private lateinit var totalPrice : TextView
    private lateinit var totalPrice2 : TextView
    private lateinit var btn : ConstraintLayout
    private lateinit var binding : FragmentOrderDetailBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_detail,container,false)
        binding = FragmentOrderDetailBinding.inflate(layoutInflater)
//        totalPrice = view.findViewById(R.id.order_detail_total_price)
//        totalCnt = view.findViewById(R.id.order_detail_cnt)
//        totalPrice2 = view.findViewById(R.id.order_detail_total_price2)

        val data:MutableList<StoreItem> = loadData()
        var adapter = OrderDetailAdapter()
        adapter.listData = data

        binding.orderDetailRecyclerview.adapter = adapter

        binding.orderDetailRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        //btn = binding.orderDetailBottomButton
//        btn.setOnClickListener {
//
//        }
        return binding.root
    }

    private fun loadData(): MutableList<StoreItem> {
        val data:MutableList<StoreItem> = mutableListOf()
        for(no in 1..3){
            val price = 10000 * no
            val name = "테스트 ${no}"
            var store = StoreItem(tbItemId = no, storeId = 0, name = name, price = price, imgUrl = "", stock = 0, order_limit = 0, createdAt = Date())
            data.add(store)
        }
        return data
    }
}