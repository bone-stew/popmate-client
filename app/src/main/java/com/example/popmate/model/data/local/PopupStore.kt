package com.example.popmate.model.data.local

data class PopupStore(
    val popupStoreId: Long,
    val bannerImgUrl: String,
    val closeDate: String,
    val closeTime: String,
    val description: String,
    val entryFee: Int,
    val eventDescription: String,
//    val latitude: Double,
//    val longitude: Double,
    val openDate: String,
    val openTime: String,
    val organizer: String,
    val placeDetail: String,
    val popupStoreImgResponses: List<PopupStoreImgResponse>,
    val popupStoreSnsResponses: List<PopupStoreSnsResponse>,
    val popupStoresNearBy: List<PopupStore>,
    val status: Int,
    val title: String,
    val views: Int,
    val reservationEnabled: Boolean,
    val department: Department,
    val categoryName: String
) {
    val openDateFormatted: String
        get() {
            return "${openDate.split("T")[0]} ~ ${closeDate.split("T")[0]}"
        }
    val openTimeFormatted: String
        get() {
            return "${openTime.split("T")[1].subSequence(0, 5)} ~ ${closeTime.split("T")[1].subSequence(0, 5)}"
        }

    val entryFeeFormatted: String
        get() {
            return if(entryFee == 0)  "무료" else "${entryFee}원"
        }
}
