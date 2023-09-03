package com.example.popmate.viewmodel.reservation

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class ReservationViewModel : ViewModel() {
    val count = ObservableField(1)
    val isReservationPending = ObservableField(false)

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

        // 3초 로딩
        Thread.sleep(3000)

        // retrofit 호출 후
        isReservationPending.set(false)

        return true
    }
}
