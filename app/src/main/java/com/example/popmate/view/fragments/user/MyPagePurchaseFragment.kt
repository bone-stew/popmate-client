package com.example.popmate.view.fragments.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.FragmentMyPagePurchaseBinding
import com.example.popmate.model.data.remote.user.Orders
import com.example.popmate.view.activities.detail.PopupDetailActivity
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
            if (data.isEmpty()) {
                binding.txtNoguma.visibility = View.VISIBLE // 뷰를 화면에 보이게 설정
            } else {
                binding.txtNoguma.visibility = View.GONE // 뷰를 화면에서 숨김
            }
            binding.myPagePurchaseRecyclerview.adapter = adapter
            binding.myPagePurchaseRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            binding.layoutPageTitle.imgArrow.setOnClickListener {
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStack()
            }
            // 아이템 클릭 이벤트 처리
            adapter.setOnItemClickListener { clickedItem ->
                val newFragment = MyPagePurchaseDetailFragment(clickedItem)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, newFragment)
                    .addToBackStack(null)
                    .commit()
            }
            adapter.setOnImgClickListener {
                val intent = Intent(context, PopupDetailActivity::class.java)
                intent.putExtra("id", it)
                context?.startActivity(intent)
            }
        }

        return binding.root
    }

}