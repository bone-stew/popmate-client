package com.example.popmate.model.data.remote.order

import java.util.Date

data class StoreItem(
    val tbItemId: Long,
    val storeId: Long,
    val name: String,
    val price: Int,
    val imgUrl: String,
    val stock: Int,
    val order_limit: Int,
    val createdAt : Date
)
