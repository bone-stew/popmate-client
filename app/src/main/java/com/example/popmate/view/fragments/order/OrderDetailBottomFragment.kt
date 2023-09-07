package com.example.popmate.view.fragments.order

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popmate.databinding.FragmentOrderDetailBottomBinding
import com.example.popmate.view.activities.order.OrderPaymentActivity


class OrderDetailBottomFragment : Fragment() {
    private lateinit var binding : FragmentOrderDetailBottomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailBottomBinding.inflate(layoutInflater)
        binding.layoutOrderDetailBottomFragment.setOnClickListener {
            val intent = Intent(requireContext(), OrderPaymentActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}