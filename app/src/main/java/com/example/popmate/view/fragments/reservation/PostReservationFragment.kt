package com.example.popmate.view.fragments.reservation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.config.BaseFragment
import com.example.popmate.databinding.FragmentPostReservationBinding
import com.example.popmate.view.adapters.reservation.MyReservationAdapter
import com.example.popmate.viewmodel.reservation.MyReservationViewModel

class PostReservationFragment :
    BaseFragment<FragmentPostReservationBinding, MyReservationViewModel>(R.layout.fragment_post_reservation) {

    override val viewModel: MyReservationViewModel by lazy {
        ViewModelProvider(this)[MyReservationViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setObserve()
    }

    private fun initView() {
        viewModel.postReservationItems.observe(viewLifecycleOwner) { reservationItems ->
            binding.rvPostReservations.apply {
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

