package com.example.popmate.model.repository.service.popupStore

import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.popupstore.HomeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreApiService {

    @GET("popup-stores/1")
    fun getStoreDetail(): Call<ApiResponse<PopupStore>>

    @GET("popup-stores/home")
    fun getStoreHome(@Query("userId") userId: Long): Call<ApiResponse<HomeResponse>>
}
