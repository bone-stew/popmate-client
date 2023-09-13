package com.example.popmate.viewmodel.reservation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.reservation.CurrentReservationResponse
import com.example.popmate.model.data.remote.reservation.ReservationRequest
import com.example.popmate.model.data.remote.reservation.Wifi
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationViewModel : ViewModel() {
    val count = ObservableField(1)
    val maxGuestCount: Int = 6
    private val isReservationPending = ObservableField(false)

    private var _popupStoreId: Long? = null
    val popupStoreId: Long?
        get() = _popupStoreId

    private var _reservationId: Long? = null
    val reservationId: Long?
        get() = _reservationId

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage


    private val _currentReservation = MutableLiveData<CurrentReservationResponse?>()

    val currentReservation: MutableLiveData<CurrentReservationResponse?> = _currentReservation


    fun getCurrentReservation(popupStoreId: Long) {
        _popupStoreId = popupStoreId
        loadCurrentReservation(_popupStoreId!!)
    }

    fun increment() {
        val currentCount = count.get() ?: 0
        if (currentCount < maxGuestCount) {
            count.set(currentCount + 1) // 6명 이하 예약해야 함
        }
    }

    fun decrement() {
        val currentCount = count.get() ?: 0
        if (currentCount > 1) {
            count.set(currentCount - 1) // 1명 이상 예약해야 함
        }
    }

    private fun loadCurrentReservation(popupStoreId: Long) {
        Log.d("smh", "loadCurrentReservation: $popupStoreId")
        ApiClient.reservationService.getCurrentReservation(popupStoreId!!)
            .enqueue(object : Callback<ApiResponse<CurrentReservationResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<CurrentReservationResponse>>,
                    response: Response<ApiResponse<CurrentReservationResponse>>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()?.data
                        result?.let {
                            Log.d("smh", "불러온 현재 예약: $it")
                            _reservationId = it.reservationId
                            _currentReservation.postValue(it)
                        }
                        Log.d("smh", "response: $response")
                    } else {
                        Log.d("smh", "response: ${response.body()?.message}")
                        if (response.code() == 404) {
                            Log.d("smh", "진행 중인 예약이 없습니다")
                            _currentReservation.postValue(null)
                        }
                    }

                }

                override fun onFailure(
                    call: Call<ApiResponse<CurrentReservationResponse>>,
                    t: Throwable
                ) {
                    Log.d("smh", "onFailure: ${t.message}")
                }

            })
    }

    fun reserve(callback: (Boolean) -> Unit) {
        val guestCount = count.get() ?: 0
        _reservationId?.let {
            // 예약하기 버튼 클릭 시 동작
            ApiClient.reservationService.reserve(_reservationId!!, ReservationRequest(guestCount, getCurrentLocation()))
                .enqueue(object : Callback<ApiResponse<Void>> {
                    override fun onResponse(
                        call: Call<ApiResponse<Void>>,
                        response: Response<ApiResponse<Void>>
                    ) {
                        Log.d("smh", "예약 성공 여부: ${response}")
                        if (response.isSuccessful) {
                            callback(true)
                        } else if (response.code() == 400) {
                            _toastMessage.value = "이미 예약되었습니다."
                            callback(false)
                        } else {
                            callback(false)
                        }

                    }

                    override fun onFailure(
                        call: Call<ApiResponse<Void>>,
                        t: Throwable
                    ) {
                        // 예약이 마감되는 경우
                        Log.d("Reservation", "onFailure: $t")
                        callback(false)
                    }
                })
        }

        // retrofit 호출
        isReservationPending.set(true)

        // retrofit 호출 후
        isReservationPending.set(false)
    }

    /**
     * 임시 wifi 정보
     */
    private fun getCurrentLocation(): List<Wifi> {
        return listOf(
            Wifi("bssid", "ssid"),
            Wifi("00:11:22:33:44:55", "ssid2")
        )
    }
}
