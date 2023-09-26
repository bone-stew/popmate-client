package com.example.popmate.viewmodel.reservation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popmate.config.BaseViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.reservation.MyReservationDetailResponse
import com.example.popmate.model.repository.ApiClient.reservationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReservationDetailViewModel : BaseViewModel() {
    var reservationId: Long = 0

    private val _myReservation = MutableLiveData<MyReservationDetailResponse>()
    val myReservation: LiveData<MyReservationDetailResponse> = _myReservation

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading // 로딩 상태

    fun loadMyReservationDetail(reservationId: Long) {
        _isLoading.value = true

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
                    _isLoading.value = false
                }

                override fun onFailure(
                    call: Call<ApiResponse<MyReservationDetailResponse>>,
                    t: Throwable
                ) {
                    Log.d("Reservation", "onFailure: $t")
                    sendErrorMessage(t)
                    _isLoading.value = false
                }
            })
    }
}
