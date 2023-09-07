package com.example.popmate.viewmodel.reservation

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.popmate.config.BaseViewModel
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.getResult
import com.example.popmate.model.data.remote.reservation.MyReservationDetailResponse
import com.example.popmate.model.repository.ApiClient.reservationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReservationDetailViewModel : BaseViewModel() {
    val reservationId: Long
        get() = 0 // reservationId를 받아오는 코드

    private val _myReservation = MutableLiveData<MyReservationDetailResponse>()
    val myReservation: LiveData<MyReservationDetailResponse> = _myReservation

    init {
        loadMyReservationDetail(1)
    }

    private fun loadMyReservationDetail(reservationId: Long) {
        Log.d("Reservation", "loadMyReservationDetail: $reservationId")
        reservationService.getMyReservation(reservationId)
            .enqueue(object : Callback<ApiResponse<MyReservationDetailResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<MyReservationDetailResponse>>,
                    response: Response<ApiResponse<MyReservationDetailResponse>>
                ) {
                    Log.d("Reservation", "response1: $response")
                    if (response.isSuccessful) {
                        val result = response.body()?.data
                        Log.d("Reservation", "response2: $response")
                        Log.d("Reservation", "result3: $result")
                        result?.let {
                            _myReservation.postValue(it)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<MyReservationDetailResponse>>,
                    t: Throwable
                ) {
                    Log.d("Reservation", "onFailure: $t")
                    sendErrorMessage(t)
                }
            })
    }
}
