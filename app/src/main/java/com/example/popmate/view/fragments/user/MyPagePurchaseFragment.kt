package com.example.popmate.view.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.FragmentMyPagePurchaseBinding
import com.example.popmate.model.data.remote.order.OrderItem
import com.example.popmate.view.adapters.user.MyPageOrderAdapter
import java.util.Date


class MyPagePurchaseFragment : Fragment() {
    private lateinit var binding: FragmentMyPagePurchaseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPagePurchaseBinding.inflate(layoutInflater)


        val data:MutableList<OrderItem> = loadData()
        val adapter = MyPageOrderAdapter()
        adapter.listData = data
        binding.myPagePurchaseRecyclerview.adapter = adapter
        binding.myPagePurchaseRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        // 아이템 클릭 이벤트 처리
        adapter.setOnItemClickListener { clickedItem ->
            // 새 프래그먼트로 교체하기
            val newFragment = MyPagePurchaseDetailFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, newFragment)
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    private fun loadData(): MutableList<OrderItem> {
        val data:MutableList<OrderItem> = mutableListOf()
        for (no in 1..5){
            var item = OrderItem(orderId = 1, storeId = no, userId = no, orderDate = Date(), status = 1, totalAmount = 3, createdAt = Date() )
            data.add(item)
        }
        return data
    }

}