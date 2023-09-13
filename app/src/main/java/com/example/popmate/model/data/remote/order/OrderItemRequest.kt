package com.example.popmate.model.data.remote.order

data class OrderItemRequest(
    val popupStore: List<PopupStoreItem>,
    val orderId: String,
    val url: String,
    val cardType: String,
    val easyPay: String,
    val method: String
)

