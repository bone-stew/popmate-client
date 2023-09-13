package com.example.popmate.model.data.remote.reservation

data class ReservationRequest(
    val guestCount: Int,
    val wifiList: List<Wifi>
)

data class Wifi(
    val bssid: String,
    val ssid: String
)
