package com.example.popmate.model.data.remote.reservation

data class MyReservationsResponse(
    val after: List<MyReservationResponse>? = null,
    val before: List<MyReservationResponse>? = null,
    val canceled: List<MyReservationResponse>? = null,
) {
    data class MyReservationResponse(
        val bannerImgUrl: String,
        val endTime: String,
        val popupStoreId: Int,
        val popupStoreTitle: String,
        val reservationId: Int,
        val reservationStatus: String,
        val startTime: String
    )
}
