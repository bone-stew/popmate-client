package com.example.popmate.model.repository.service.order

import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.order.OrderResponse
import com.example.popmate.model.data.remote.order.PopupStoreItem
import com.example.popmate.model.data.remote.order.PopupStoreItemsResponse
import com.example.popmate.model.data.remote.order.StockCheckItemsResponse
import com.example.popmate.model.data.remote.user.OrderListItemsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderApiService {
    @GET("popup-stores/{popupStoreId}/items")
    fun getPopupStoreItems(@Path("popupStoreId") popupStoreId: Int): Call<ApiResponse<PopupStoreItemsResponse>>

    @POST("orders/new")
    fun orderItems(
        @Body popupStore: List<PopupStoreItem>,
        @Query("orderId") orderId: String,
        @Query("url") url: String,
        @Query("cardType") cardType: String,
        @Query("easyPay") easyPay: Any?,
        @Query("method") method: String
    ): Call<ApiResponse<OrderResponse>>

    @GET("orders/me")
    fun getOrderListItems():Call<ApiResponse<OrderListItemsResponse>>

    @POST("orders/stockCheck")
    fun checkOrderItemsStock(
        @Body stockCheckRequest: ArrayList<PopupStoreItem>) : Call<ApiResponse<StockCheckItemsResponse>>
}