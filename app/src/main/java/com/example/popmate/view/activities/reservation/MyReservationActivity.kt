package com.example.popmate.view.activities.reservation

import android.os.Bundle
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityMyReservationBinding
import com.example.popmate.view.adapters.reservation.ViewPagerFragmentAdapter
import com.example.popmate.viewmodel.reservation.MyReservationViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MyReservationActivity :
    BaseActivity<ActivityMyReservationBinding>(R.layout.activity_my_reservation) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MyReservationViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initView()
    }

    private fun initView() {
        /**
         * ViewPager2에 Fragment 연결
         */
        val viewpagerFragmentAdapter = ViewPagerFragmentAdapter(this)
        binding.vpVisit.adapter = viewpagerFragmentAdapter

        /**
         * TabLayout에 ViewPager2 연결
         */
        val tabTextList = listOf("예약전", "예약후", "취소됨")

        val layoutVisitTab = binding.layoutVisitTab
        val vpVisit = binding.vpVisit
        TabLayoutMediator(layoutVisitTab, vpVisit) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }
}
