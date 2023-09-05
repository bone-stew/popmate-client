package com.example.popmate.view.fragments.reservation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.config.BaseFragment
import com.example.popmate.databinding.FragmentPreReservationBinding
import com.example.popmate.view.adapters.reservation.MyReservationAdapter
import com.example.popmate.viewmodel.reservation.MyReservationViewModel

class PreReservationFragment :
    BaseFragment<FragmentPreReservationBinding, MyReservationViewModel>(R.layout.fragment_pre_reservation) {

    override val viewModel: MyReservationViewModel by lazy {
        ViewModelProvider(this)[MyReservationViewModel::class.java]
    }

    // OnViewCreated(): 뷰가 생성되고 난 후 호출되는 메서드
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView();
    }

    private fun initView() {
        viewModel.preReservationItems.observe(viewLifecycleOwner) { reservationItems ->
            binding.rvPreReservations.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = MyReservationAdapter(reservationItems)
            }
        }
    }
}

