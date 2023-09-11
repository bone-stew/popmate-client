package com.example.popmate.model.data.remote.user


data class Orders(
    val createdAt: String,
    val orderId: Int,
    val userId: Int,
    val popupStoreId: Int,
    val title: String,
    val placeDetail: String,
    val bannerImgUrl: String,
    val total_amount: Int,
    val status: Int,
    val orderTossId: String,
    val url: String,
    val cardType: String,
    val easyPay: Any?,
    val method: String,
    val orderItemList: List<OrderListItem>
)
