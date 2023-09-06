package com.example.popmate.view.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.databinding.FragmentMyPagePurchaseDetailBinding
import com.example.popmate.view.adapters.user.MyPageOrderDetailAdapter


class MyPagePurchaseDetailFragment : Fragment() {
    private lateinit var binding : FragmentMyPagePurchaseDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPagePurchaseDetailBinding.inflate(layoutInflater)

        val adapter = MyPageOrderDetailAdapter()

        binding.recyclerViewMyPagePuchaseDetail.adapter = adapter
        binding.recyclerViewMyPagePuchaseDetail.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }



}