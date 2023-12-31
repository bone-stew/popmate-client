package com.example.popmate.view.activities.reservation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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
        initEvent()
    }

    private fun initView() {
        val popupStoreId: Long = intent.getLongExtra("id", -1)
        if (popupStoreId == -1L) {
            showToast("존재하는 팝업스토어가 아닙니다")
            finish()
        }
        Log.d("smh", "예약시 진입한 popupStoreId: $popupStoreId")
        viewModel.getCurrentReservation(popupStoreId)
        viewModel.currentReservation.observe(this) {
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
                binding.tvPopupStoreOpenTime.text =
                    DateTimeUtils().toTimeString(it.popupStoreOpenTime)
                binding.tvPopupStoreCloseTime.text =
                    DateTimeUtils().toTimeString(it.popupStoreCloseTime)
            } else {
                showToast("진행 중인 예약이 없습니다")
                finish()
            }
        }
    }

    private fun setObserve() {
        binding.btnMinus.setOnClickListener {
            viewModel.decrement()
            if (viewModel.count.get() == viewModel.maxGuestCount) {
                binding.btnMinus.isEnabled = false
            }
        }
        binding.btnPlus.setOnClickListener {
            viewModel.increment()
            if (viewModel.count.get() == 1) {
                binding.btnMinus.isEnabled = false
            }
        }
        binding.btnReserve.setOnClickListener {
            viewModel.reserve { isSuccess ->
                if (isSuccess) {
                    Log.d("smh", "예약 성공")
                    showReservationSuccessDialog()
                    binding.btnReserve.isEnabled = false
                    binding.btnReserve.text = "예약 완료"
                } else {
                    Log.d("smh", "예약 실패")
                    showToast("예약이 마감되었습니다.")
                    initView() // 예약 실패 시 다음 예약을 위해 초기화
                }
            }
        }
        viewModel.toastMessage.observe(this, Observer { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initEvent() {
        binding.layoutPageTitle.imgArrow.setOnClickListener {
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showReservationSuccessDialog() {
        val dialog = ReservationSuccessDialogFragment()
        val bundle = Bundle()
        bundle.putLong("userReservationId", viewModel.userReservationId!!)
        bundle.putLong("reservationId", viewModel.reservationId!!)
        bundle.putLong("popupStoreId", viewModel.popupStoreId!!)
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "ReservationSuccessDialogFragment")
    }
}
