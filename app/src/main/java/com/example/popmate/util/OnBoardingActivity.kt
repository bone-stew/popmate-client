package com.example.popmate.util

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.popmate.databinding.ActivityOnBoardingBinding
import com.example.popmate.view.activities.MainActivity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class OnBoardingActivity : AppCompatActivity(), OnboardingAdapter.OnboardingClickListener {

    private lateinit var viewPager: ViewPager2
    private lateinit var dotsIndicator: DotsIndicator
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.pager2
        dotsIndicator = binding.dotsIndicator

        val adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter

        binding.dotsIndicator.setViewPager2(viewPager)
    }

    override fun onButtonClick() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}