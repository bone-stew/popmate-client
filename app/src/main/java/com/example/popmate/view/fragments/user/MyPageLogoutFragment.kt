package com.example.popmate.view.fragments.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.popmate.R
import com.example.popmate.databinding.FragmentMyPageLogoutBinding


class MyPageLogoutFragment : Fragment() {
    private lateinit var binding : FragmentMyPageLogoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageLogoutBinding.inflate(layoutInflater)
        binding.layoutMyPageLogoutPurchase.setOnClickListener {
            val newFragment = MyPagePurchaseFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, newFragment)
                .addToBackStack(null)
                .commit()
        }
        return binding.root
    }

}