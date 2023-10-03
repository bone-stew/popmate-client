package com.example.popmate.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popmate.databinding.FragmentOnboarding3Binding


class OnboardingFragment3 : Fragment() {

    private lateinit var binding : FragmentOnboarding3Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboarding3Binding.inflate(layoutInflater)


        binding.onboardingbtn.setOnClickListener{
            (requireActivity() as? OnboardingAdapter.OnboardingClickListener)?.onButtonClick()
        }
        return binding.root
    }
}