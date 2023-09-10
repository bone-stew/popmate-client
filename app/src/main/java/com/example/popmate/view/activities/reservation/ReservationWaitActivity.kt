package com.example.popmate.view.activities.reservation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityReservationWaitBinding
import com.example.popmate.util.DateTimeUtils
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

        initView()
        setObserve()
    }

    private fun initView() {
        val popupStoreId: Long = intent.getLongExtra("id", 1)
        Log.d("Reservation", "popupStoreId: $popupStoreId")
        viewModel.getCurrentReservation(popupStoreId)
        Log.d("Reservation", "currentReservation: ${viewModel.currentReservation.value}")
        viewModel.currentReservation.observe(this) {
            Log.d("Reservation", "currentReservation: $it")
            if (it != null) {
                binding.layoutPageTitle.titleText = it.popupStoreTitle
                binding.btnMinus.isEnabled = true
                binding.btnPlus.isEnabled = true
                binding.btnReserve.isEnabled = true
                binding.tvVisitStatus.text = it.status
                binding.tvEntryStartTime.text = DateTimeUtils().toHourMinuteString(it.startTime)
                binding.tvEntryEndTime.text = DateTimeUtils().toHourMinuteString(it.endTime)
                binding.tvPopupStoreName.text = it.popupStoreTitle
                binding.tvPopupStoreDescription.text = it.popupStoreDescription
                binding.tvPopupStoreOpenTime.text = DateTimeUtils().toTimeString(it.popupStoreOpenTime)
                binding.tvPopupStoreCloseTime.text = DateTimeUtils().toTimeString(it.popupStoreCloseTime)
            }
        }
    }

    private fun setObserve() {
        binding.btnMinus.setOnClickListener {
            viewModel.decrement()
        }
        binding.btnPlus.setOnClickListener {
            viewModel.increment()
        }
        binding.btnReserve.setOnClickListener {
            val isReserved = viewModel.reserve()
            if (isReserved) {
                Log.d("Reservation", "예약 성공")
                showReservationSuccessDialog()
            } else {
                Log.d("Reservation", "예약 실패")
                showToast("예약이 마감되었습니다.")
                initView() // 예약 실패 시 다음 예약을 위해 초기화
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
