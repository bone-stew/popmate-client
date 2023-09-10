package com.example.popmate.model.data.remote.reservation

data class ReservationRequest (
    val guestCount: Int,
    val latitude: Double? = 127.123, // TODO: API 스펙 변경 예정
    val longitude: Double? = 37.123,
)
