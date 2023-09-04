package com.example.popmate.view.activities.reservation

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityMyReservationBinding
import com.example.popmate.view.adapters.reservation.ViewPagerFragmentAdapter
import com.example.popmate.viewmodel.reservation.MyReservationViewModel

class MyReservationActivity :
    BaseActivity<ActivityMyReservationBinding>(R.layout.activity_my_reservation) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MyReservationViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initTab()
    }

    private fun initTab() {
        // FragmentStateAdapter 생성
        val viewpagerFragmentAdapter = ViewPagerFragmentAdapter(this)

        // ViewPager2에 어댑터 설정
        binding.vpVisit.adapter = viewpagerFragmentAdapter
    }
}
