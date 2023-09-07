package com.example.popmate.model.data.remote.reservation

import java.time.LocalDateTime

data class MyReservationDetailResponse(
    val guestCount: Int,
    val popupStoreImageUrl: String,
    val popupStorePlaceDetail: String,
    val popupStoreTitle: String,
    val reservationQrImageUrl: String,
    val reservationStatus: String,
    val visitEndTime: String,
    val visitStartTime: String
)

/**
 * String에서 LocalDateTime으로 변환
 */
fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this)
}

