package com.example.popmate.model.repository.service.order

import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.order.OrderResponse
import com.example.popmate.model.data.remote.order.PopupStoreItem
import com.example.popmate.model.data.remote.order.PopupStoreItemsResponse
import com.example.popmate.model.data.remote.user.OrderListItemsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderApiService {
    @GET("popup-stores/1/items")
    fun getPopupStoreItems(): Call<ApiResponse<PopupStoreItemsResponse>>

    @POST("orders/new")
    fun orderItems(
        @Body popupStore: List<PopupStoreItem>,
        orderId: String,
        url: String,
        cardType: String,
        easyPay: Any?,
        method: String
    ): Call<ApiResponse<OrderResponse>>

    @GET("orders/me")
    fun getOrderListItems():Call<ApiResponse<OrderListItemsResponse>>
}