package com.example.popmate.model.data.remote.order

import java.util.Date

data class OrderItem(
    val orderId: Int,
    val storeId: Int,
    val userId: Int,
    val orderDate : Date,
    val status: Int,
    val totalAmount: Int,
    val createdAt : Date
)
