package com.example.popmate.view.activities.reservation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityMyReservationDetailBinding
import com.example.popmate.util.DateTimeUtils
import com.example.popmate.util.ProgressDialog
import com.example.popmate.view.fragments.ReservationCancelDialogFragment
import com.example.popmate.viewmodel.reservation.MyReservationDetailViewModel

class MyReservationDetailActivity :
    BaseActivity<ActivityMyReservationDetailBinding>(R.layout.activity_my_reservation_detail) {

    private val viewModel by viewModels<MyReservationDetailViewModel>()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[MyReservationDetailViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        progressDialog = ProgressDialog(this)
        progressDialog.start()
        progressDialog.setCancelable(false)

        initView()
        initEvent()
    }

    private fun initView() {
        viewModel.userReservationId = intent.getLongExtra("userReservationId", -1)

        viewModel.loadMyReservationDetail(viewModel.userReservationId)
        viewModel.myReservation.observe(this) {
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
                binding.imgVisitedSuccess.visibility = View.VISIBLE
            } else if (it.reservationStatus == "RESERVED") {
                binding.tvCancelReservation.visibility = View.VISIBLE
            }
        }
        progressDialog.dismiss()
    }

    private fun initEvent() {
        binding.layoutTitleSimple.imgArrow.setOnClickListener {
            finish()
        }
        binding.tvCancelReservation.setOnClickListener {
            // 예약 취소 다이얼로그 띄우기
            val dialog = ReservationCancelDialogFragment()
            val bundle = Bundle()
            bundle.putLong("userReservationId", viewModel.userReservationId)
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, "ReservationCancelDialogFragment")
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
