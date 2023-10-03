package com.example.popmate.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val NUM_PAGES = 4 // Onboarding 페이지 개수
    interface OnboardingClickListener {
        fun onButtonClick()
    }
    override fun createFragment(position: Int): Fragment {
        // 각 페이지의 Fragment를 생성하여 반환합니다.
        return when (position) {
            0 -> OnboardingFragment()
            1 -> OnboardingFragment1()
            2 -> OnboardingFragment2()
            3 -> OnboardingFragment3()
            else -> OnboardingFragment1()
        }
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }
}
