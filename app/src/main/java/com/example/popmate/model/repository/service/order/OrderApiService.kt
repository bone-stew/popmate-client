package com.example.popmate.model.repository.service.order

import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.order.PopupStoreItemsResponse
import retrofit2.Call
import retrofit2.http.GET

interface OrderApiService {
    @GET("popup-stores/1/items")
    fun getPopupStoreItems(): Call<ApiResponse<PopupStoreItemsResponse>>
}