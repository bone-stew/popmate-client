package com.example.popmate.view.activities.reservation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityReservationWaitBinding
import com.example.popmate.viewmodel.reservation.ReservationViewModel

class ReservationWaitActivity :
    BaseActivity<ActivityReservationWaitBinding>(R.layout.activity_reservation_wait) {

    private val viewModel by viewModels<ReservationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setObserve();
    }

    private fun setObserve() {
        binding.btnMinus.setOnClickListener {
            viewModel.decrement()
        }
        binding.btnPlus.setOnClickListener {
            viewModel.increment()
        }
        binding.btnReserve.setOnClickListener {
            viewModel.onReserveButtonClick()
        }
    }
}