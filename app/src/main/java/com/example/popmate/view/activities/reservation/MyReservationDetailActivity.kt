package com.example.popmate.view.activities.reservation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityMyReservationDetailBinding
import com.example.popmate.util.DateTimeUtils
import com.example.popmate.viewmodel.reservation.MyReservationDetailViewModel

class MyReservationDetailActivity :
    BaseActivity<ActivityMyReservationDetailBinding>(R.layout.activity_my_reservation_detail) {

    private val viewModel by viewModels<MyReservationDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[MyReservationDetailViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initView()
        initEvent()
    }

    private fun initView() {
        viewModel.reservationId = intent.getLongExtra("reservationId", -1)
        println("viewModel.reservationId = ${viewModel.reservationId}")

        viewModel.loadMyReservationDetail(viewModel.reservationId)
        viewModel.myReservation.observe(this) {
            Log.d("smh", "initView: $it")
            binding.tvPopupStoreName.text = it.popupStoreTitle
            binding.tvVisitPeopleCount.text = it.guestCount.toString()
            binding.tvVisitLocation.text = it.popupStorePlaceDetail
            binding.tvVisitPeopleCount.text = it.guestCount.toString()
            binding.tvVisitStartTime.text =
                DateTimeUtils().toTimeString(it.visitStartTime)
            binding.tvVisitEndTime.text =
                DateTimeUtils().toTimeString(it.visitEndTime)
            Glide.with(this)
                .load(it.popupStoreImageUrl)
                .into(binding.imgPopupStore)
            Glide.with(this)
                .load(it.reservationQrImageUrl)
                .into(binding.imgReservationQr)
            if (it.reservationStatus == "VISITED") {
                binding.imgVisitedSuccess.visibility = android.view.View.VISIBLE
            }
        }
    }

    private fun initEvent() {
        binding.layoutTitleSimple.imgArrow.setOnClickListener {
            finish()
        }
    }
}
