package com.example.popmate.model.repository.service.popupStore

import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.popupstore.HomeResponse
import com.example.popmate.model.data.remote.popupstore.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreApiService {

    @GET("popup-stores/{popupStoreId}")
    fun getStoreDetail(@Path("popupStoreId") popupStoreId: Long, @Query("userId") userId: Long): Call<ApiResponse<PopupStore>>

    @GET("popup-stores/home")
    fun getStoreHome(@Query("userId") userId: Long): Call<ApiResponse<HomeResponse>>

    @GET("popup-stores")
    fun getStoreSearch(
        @Query("isOpeningSoon") isOpeningSoon: Boolean?,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?,
        @Query("keyword") keyword: String?,
        @Query("offSetRows") offSetRows: Int?,
        @Query("rowsToGet") rowsToGet: Int?
    ): Call<ApiResponse<SearchResponse>>
}
