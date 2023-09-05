package com.example.popmate.viewmodel.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popmate.config.BaseViewModel
import com.example.popmate.model.data.remote.reservation.MyReservationRequest

class MyReservationViewModel : BaseViewModel() {

    private val _preReservationItems = MutableLiveData<List<MyReservationRequest>>()
    private val _postReservationItems = MutableLiveData<List<MyReservationRequest>>()
    private val _cancelReservationItems = MutableLiveData<List<MyReservationRequest>>()
    val preReservationItems: LiveData<List<MyReservationRequest>> = _preReservationItems
    val postReservationItems: LiveData<List<MyReservationRequest>> = _postReservationItems
    val cancelReservationItems: LiveData<List<MyReservationRequest>> = _cancelReservationItems

    init {
        // 초기 데이터 로드 또는 API 호출 등
        _preReservationItems.value = getSampleMyReservationItems()
        _postReservationItems.value = getSampleMyReservationItems()
        _cancelReservationItems.value = getSampleMyReservationItems()
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
            ),
        )
    }

}
