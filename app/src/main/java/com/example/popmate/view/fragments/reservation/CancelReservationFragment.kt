package com.example.popmate.view.fragments.reservation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.config.BaseFragment
import com.example.popmate.databinding.FragmentCancelReservationBinding
import com.example.popmate.view.adapters.reservation.MyReservationAdapter
import com.example.popmate.viewmodel.reservation.MyReservationViewModel

class CancelReservationFragment :
    BaseFragment<FragmentCancelReservationBinding, MyReservationViewModel>(R.layout.fragment_cancel_reservation) {

    override val viewModel: MyReservationViewModel by lazy {
        ViewModelProvider(this)[MyReservationViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setObserve()
    }

    private fun initView() {
        viewModel.cancelReservationItems.observe(viewLifecycleOwner) { reservationItems ->
            binding.rvCancelReservations.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = MyReservationAdapter(reservationItems)
            }
        }
    }

    private fun setObserve() {
        viewModel.noReservationsTextVisibility.observe(viewLifecycleOwner) { visibility ->
            binding.noReservationsText.visibility = visibility
        }
    }
}

