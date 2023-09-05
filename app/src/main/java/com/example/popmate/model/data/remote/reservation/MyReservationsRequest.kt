package com.example.popmate.model.data.remote.reservation

data class MyReservationRequest(
    val bannerImgUrl: String,
    val endTime: String,
    val popupStoreId: Int,
    val popupStoreTitle: String,
    val reservationId: Int,
    val reservationStatus: String,
    val startTime: String
)
