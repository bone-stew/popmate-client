package com.example.popmate.model.data.remote.reservation

data class CurrentReservationResponse(
    val endTime: String,
    val popupStoreCloseTime: String,
    val popupStoreDescription: String,
    val popupStoreOpenTime: String,
    val popupStoreTitle: String,
    val reservationId: Int,
    val startTime: String,
    val status: String
)
