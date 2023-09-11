package com.example.popmate.model.data.remote.user

import com.example.popmate.model.data.remote.order.PopupStoreItem

data class OrderListItem(
    val createdAt: String,
    val orderItemId: Int,
    val order: Any?, // null 값을 허용하는 경우 Any?로 선언합니다.
    val totalQuantity: Int,
    val totalAmount: Int,
    val popupStoreItem : PopupStoreItem
)
