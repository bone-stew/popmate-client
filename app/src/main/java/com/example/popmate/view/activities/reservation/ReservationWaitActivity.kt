package com.example.popmate.view.activities.reservation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityReservationWaitBinding
import com.example.popmate.view.fragments.ReservationSuccessDialogFragment
import com.example.popmate.viewmodel.reservation.ReservationViewModel

class ReservationWaitActivity :
    BaseActivity<ActivityReservationWaitBinding>(R.layout.activity_reservation_wait) {

    private val viewModel by viewModels<ReservationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[ReservationViewModel::class.java]
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
            val isReserved = viewModel.onReserveButtonClick()
            if (isReserved) {
                Log.d("Reservation", "예약 성공")
                showReservationSuccessDialog()
            } else {
                Log.d("Reservation", "예약 실패")
                showToast("예약에 실패했습니다.")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showReservationSuccessDialog() {
        val dialog = ReservationSuccessDialogFragment()
        dialog.show(supportFragmentManager, "ReservationSuccessDialogFragment")
    }
}