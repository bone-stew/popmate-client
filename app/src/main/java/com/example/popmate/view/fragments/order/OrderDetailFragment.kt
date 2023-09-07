//package com.example.popmate.view.framents.order
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.popmate.R
//import com.example.popmate.databinding.FragmentOrderDetailBinding
//import com.example.popmate.model.data.remote.order.PopupStoreItem
//import com.example.popmate.view.adapters.order.OrderDetailAdapter
//
//
//class OrderDetailFragment(data: ArrayList<PopupStoreItem>) : Fragment() {
//
//    private lateinit var binding : FragmentOrderDetailBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentOrderDetailBinding.inflate(layoutInflater)
//        return binding.root
//        var adapter = OrderDetailAdapter()
//        binding.orderDetailRecyclerview.adapter = adapter
//        binding.orderDetailRecyclerview.layoutManager = LinearLayoutManager(requireContext())
//
//    }
//
//}