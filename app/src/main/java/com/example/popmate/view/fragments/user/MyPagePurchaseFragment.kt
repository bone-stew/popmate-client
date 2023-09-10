package com.example.popmate.view.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.FragmentMyPagePurchaseBinding
import com.example.popmate.model.data.remote.user.Orders
import com.example.popmate.view.adapters.user.MyPageOrderAdapter
import com.example.popmate.viewmodel.user.OrderListViewModel


class MyPagePurchaseFragment : Fragment() {
    private lateinit var binding: FragmentMyPagePurchaseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPagePurchaseBinding.inflate(layoutInflater)

        val model : OrderListViewModel by viewModels()
        model.getOrderListItems().observe(viewLifecycleOwner){
            binding.orderlistitem = it
            val data: MutableList<Orders> = binding.orderlistitem?.orderListItemResponses?.toMutableList() ?: mutableListOf()
            val adapter = MyPageOrderAdapter()
            adapter.listData = data
            binding.myPagePurchaseRecyclerview.adapter = adapter
            binding.myPagePurchaseRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            // 아이템 클릭 이벤트 처리
            adapter.setOnItemClickListener { clickedItem ->
                val newFragment = MyPagePurchaseDetailFragment(clickedItem)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, newFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        return binding.root
    }

}