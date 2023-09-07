package com.example.popmate.viewmodel.reservation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popmate.config.BaseViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.reservation.MyReservationsResponse
import com.example.popmate.model.data.remote.reservation.MyReservationsResponse.MyReservationResponse
import com.example.popmate.model.repository.ApiClient.reservationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReservationViewModel : BaseViewModel() {

    private val _preReservationItems = MutableLiveData<List<MyReservationResponse>>()
    val preReservationItems: LiveData<List<MyReservationResponse>> = _preReservationItems

    private val _postReservationItems = MutableLiveData<List<MyReservationResponse>>()
    val postReservationItems: LiveData<List<MyReservationResponse>> = _postReservationItems

    private val _cancelReservationItems = MutableLiveData<List<MyReservationResponse>>()
    val cancelReservationItems: LiveData<List<MyReservationResponse>> = _cancelReservationItems

    private val _noReservationsTextVisibility = MutableLiveData<Int>()
    val noReservationsTextVisibility: LiveData<Int> = _noReservationsTextVisibility

    init {
        loadReservationItems()
    }

    private fun loadReservationItems() {
        reservationService.getMyReservations()
            .enqueue(object : Callback<ApiResponse<MyReservationsResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<MyReservationsResponse>>,
                    response: Response<ApiResponse<MyReservationsResponse>>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()?.data
                        if (result != null) {
                            result.before?.takeIf { it.isNotEmpty() }?.apply {
                                _preReservationItems.postValue(this)
                            } ?: run {
                                _noReservationsTextVisibility.postValue(View.VISIBLE)
                            }
                            result.after?.takeIf { it.isNotEmpty() }?.apply {
                                _postReservationItems.postValue(this)
                            } ?: run {
                                _noReservationsTextVisibility.postValue(View.VISIBLE)
                            }
                            result.canceled?.takeIf { it.isNotEmpty() }?.apply {
                                _cancelReservationItems.postValue(this)
                            } ?: run {
                                _noReservationsTextVisibility.postValue(View.VISIBLE)
                            }
                        } else {
                            _noReservationsTextVisibility.postValue(View.VISIBLE)
                        }
                    } else {
                        _noReservationsTextVisibility.postValue(View.VISIBLE)
                        // Handle API error here
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<MyReservationsResponse>>,
                    t: Throwable
                ) {
                    TODO("Not yet implemented")
                }
            })
    }
}
