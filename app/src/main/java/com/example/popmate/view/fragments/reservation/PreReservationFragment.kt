package com.example.popmate.view.fragments.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.config.BaseFragment
import com.example.popmate.databinding.FragmentPreReservationBinding
import com.example.popmate.model.data.remote.reservation.MyReservationRequest
import com.example.popmate.view.adapters.reservation.PreReservationAdapter
import com.example.popmate.viewmodel.reservation.MyReservationViewModel

class PreReservationFragment :
    BaseFragment<FragmentPreReservationBinding, MyReservationViewModel>(R.layout.fragment_pre_reservation) {

    override val viewModel: MyReservationViewModel by lazy {
        ViewModelProvider(this).get(MyReservationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 예약 아이템 목록을 가져오는 함수 (예시)
        val reservationItems: List<MyReservationRequest> = getSampleMyReservationItems()

        // 리사이클러뷰 초기화
        val recyclerView = binding.rvPreReservations
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 리사이클러뷰 어댑터 설정
        val adapter = PreReservationAdapter(reservationItems)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getSampleMyReservationItems(): List<MyReservationRequest> {
        return mutableListOf(
            MyReservationRequest(
                "imgUrl",
                "2021-10-10",
                1,
                "제목",
                1,
                "내용",
                "2021-10-10"
            ),
            MyReservationRequest(
                "imgUrl",
                "2021-10-10",
                1,
                "제목",
                1,
                "내용",
                "2021-10-10"
            )
        )
    }
}
