package com.example.popmate.viewmodel.reservation

import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.reservation.CurrentReservationResponse
import com.example.popmate.model.data.remote.reservation.ReservationRequest
import com.example.popmate.model.repository.ApiClient
import com.example.popmate.view.activities.reservation.ReservationWaitActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationViewModel : ViewModel() {
    val count = ObservableField(1)
    val isReservationPending = ObservableField(false)

    private var _popupStoreId: Long? = null
    val popupStoreId: Long?
        get() = _popupStoreId

    private var _reservationId: Long? = null
    val reservationId: Long?
        get() = _reservationId


    private val _currentReservation: MutableLiveData<CurrentReservationResponse> by lazy {
        MutableLiveData<CurrentReservationResponse>().also {
//            loadCurrentReservation()
        }
    }
    val currentReservation: LiveData<CurrentReservationResponse> = _currentReservation


    fun getCurrentReservation(popupStoreId: Long) {
        _popupStoreId = popupStoreId
        loadCurrentReservation(_popupStoreId!!)
    }

    fun increment() {
        val currentCount = count.get() ?: 0
        count.set(currentCount + 1)
    }

    fun decrement() {
        val currentCount = count.get() ?: 0
        if (currentCount > 1) {
            count.set(currentCount - 1)
        }
    }

    private fun loadCurrentReservation(popupStoreId: Long) {
        Log.d("Reservation", "loadCurrentReservation: $popupStoreId")
        ApiClient.reservationService.getCurrentReservation(popupStoreId!!)
            .enqueue(object : Callback<ApiResponse<CurrentReservationResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<CurrentReservationResponse>>,
                    response: Response<ApiResponse<CurrentReservationResponse>>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()?.data
                        result?.let {
                            Log.d("Reservation", "result: $result")
                            Log.d("Reservation", "before _reservationId: ${_reservationId}")
                            _reservationId = it.reservationId
                            _currentReservation.postValue(it)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<CurrentReservationResponse>>,
                    t: Throwable
                ) {
                    Log.d("Reservation", "onFailure: $t")
                }

            })
    }

    fun reserve(): Boolean {
        val guestCount = count.get() ?: 0
        var isSuccessful = false
        _reservationId?.let {
            // 예약하기 버튼 클릭 시 동작
            ApiClient.reservationService.reserve(_reservationId!!, ReservationRequest(guestCount))
                .enqueue(object : Callback<ApiResponse<Void>> {
                    override fun onResponse(
                        call: Call<ApiResponse<Void>>,
                        response: Response<ApiResponse<Void>>
                    ) {
                        if (response.isSuccessful) {
                            isSuccessful = true
                            val result = response.body()?.data
                            Log.d("Reservation", "예약 완료: $result")
                        }
                        Log.d("Reservation", "response: $response")
                    }

                    override fun onFailure(
                        call: Call<ApiResponse<Void>>,
                        t: Throwable
                    ) {
                        // 예약이 마감되는 경우
                        Log.d("Reservation", "onFailure: $t")
                    }
                })
        }

        // retrofit 호출
        isReservationPending.set(true)

        // retrofit 호출 후
        isReservationPending.set(false)

        return isSuccessful
    }
}
