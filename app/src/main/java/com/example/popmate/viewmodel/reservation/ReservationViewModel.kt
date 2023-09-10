package com.example.popmate.viewmodel.reservation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.reservation.CurrentReservationResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Response

class ReservationViewModel : ViewModel() {
    val count = ObservableField(1)
    val isReservationPending = ObservableField(false)

    private var _popupStoreId: Long? = null
    val popupStoreId: Long?
        get() = _popupStoreId // popupStoreId를 받아오는 코드

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

    fun onReserveButtonClick(): Boolean {
        // TODO: 예약하기 버튼 클릭 시 동작

        if (1 != 1) {
            return false // 예약 실패
        }

        // retrofit 호출
        isReservationPending.set(true)

        // retrofit 호출 후
        isReservationPending.set(false)

        return true
    }

    private fun loadCurrentReservation(popupStoreId: Long) {
        Log.d("Reservation", "loadCurrentReservation: $popupStoreId")
        ApiClient.reservationService.getCurrentReservation(popupStoreId!!)
            .enqueue(object : retrofit2.Callback<ApiResponse<CurrentReservationResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<CurrentReservationResponse>>,
                    response: Response<ApiResponse<CurrentReservationResponse>>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()?.data
                        result?.let {
                            Log.d("Reservation", "result: $result")
                            _currentReservation.postValue(it)                        }
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<CurrentReservationResponse>>,
                    t: Throwable
                ) {
                }

            })
    }
}
