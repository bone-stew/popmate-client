package com.example.popmate.view.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.databinding.FragmentMyPagePurchaseDetailBinding
import com.example.popmate.model.data.remote.order.StoreItem
import com.example.popmate.view.adapters.user.MyPageOrderDetailAdapter
import java.util.Date


class MyPagePurchaseDetailFragment : Fragment() {
    private lateinit var binding : FragmentMyPagePurchaseDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPagePurchaseDetailBinding.inflate(layoutInflater)

        val data:MutableList<StoreItem> = loadData()
        val adapter = MyPageOrderDetailAdapter()
        adapter.listData = data
        binding.recyclerViewMyPagePuchaseDetail.adapter = adapter
        binding.recyclerViewMyPagePuchaseDetail.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    private fun loadData(): MutableList<StoreItem> {
        val data:MutableList<StoreItem> = mutableListOf()
        for(no in 1..5){
            val price = 10000 * no
            val name = "테스트 ${no}"
            var store = StoreItem(tbItemId = no, storeId = 0, name = name, price = price, imgUrl = "", stock = 0, order_limit = 0, createdAt = Date())
            data.add(store)
        }
        return data
    }


}