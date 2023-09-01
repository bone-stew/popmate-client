package com.example.popmate.viewmodel.reservation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class ReservationViewModel : ViewModel() {
    val count = ObservableField(1)

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
}
