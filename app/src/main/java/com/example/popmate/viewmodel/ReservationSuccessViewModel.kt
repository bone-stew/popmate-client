package com.example.popmate.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.reservation.MyReservationDetailResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationSuccessViewModel : ViewModel() {
    var reservationId: Long = 0

    private val _myReservation = MutableLiveData<MyReservationDetailResponse>()
    val myReservation: LiveData<MyReservationDetailResponse> = _myReservation

    fun getReservationInfo() {
        Log.i("ReservationSuccessViewModel", "getReservationInfo: $reservationId")
        ApiClient.reservationService.getMyReservation(reservationId)
            .enqueue(object : Callback<ApiResponse<MyReservationDetailResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<MyReservationDetailResponse>>,
                    response: Response<ApiResponse<MyReservationDetailResponse>>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()?.data
                            result?.let {
                            _myReservation.postValue(it)
                        }
                        Log.d("Reservation", "result: $result")
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<MyReservationDetailResponse>>,
                    t: Throwable
                ) {
                    Log.d("Reservation", "onFailure: $t")
                }
            })
    }
}
