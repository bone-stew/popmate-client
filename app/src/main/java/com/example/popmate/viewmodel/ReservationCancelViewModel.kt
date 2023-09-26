package com.example.popmate.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popmate.config.BaseViewModel
import com.example.popmate.model.data.local.Chat
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.reservation.MyReservationDetailResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationCancelViewModel : BaseViewModel() {

    var reservationId: Long = 0

    fun cancelReservation(reservationId: Long) {
        ApiClient.reservationService.cancelReservation(reservationId)
            .enqueue(object : Callback<ApiResponse<Void>> {
                override fun onResponse(
                    call: Call<ApiResponse<Void>>,
                    response: Response<ApiResponse<Void>>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()?.data
                        Log.d("smh", "onResponse: $result")
                    } else {
                        Log.d("smh", "onResponse fail: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<ApiResponse<Void>>, t: Throwable) {
                    Log.d("smh", "onFailure: ${t.message}")
                }
            })
    }
}
