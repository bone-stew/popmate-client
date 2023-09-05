package com.example.popmate.model.repository.service.popupStore

import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.model.data.remote.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface StoreApiService {

    @GET("popup-stores/1")
    fun getStoreDetail(): Call<ApiResponse<PopupStore>>
}