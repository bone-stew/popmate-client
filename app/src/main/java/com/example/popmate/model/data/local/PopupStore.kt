package com.example.popmate.model.data.local

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PopupStore(
    val id: Long,
    val bannerImgUrl: String,
    val closeDate: String,
    val closeTime: String,
    val departmentDescription: String,
    val departmentName: String,
    val description: String,
    val entryFee: Int,
    val eventDescription: String,
    val latitude: Double,
    val longitude: Double,
    val openDate: String,
    val openTime: String,
    val organizer: String,
    val placeDetail: String,
    val popupStoreImgResponses: List<Banner>,
    val popupStoreSnsResponses: List<PopupStoreSnsResponse>,
    val status: Int,
    val title: String,
    val views: Int
) {
    val openDateFormatted: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val openDateStr = dateFormat.format(openDate)
            val closeDateStr = dateFormat.format(closeDate)
            return "$openDateStr ~ $closeDateStr"
        }
}
