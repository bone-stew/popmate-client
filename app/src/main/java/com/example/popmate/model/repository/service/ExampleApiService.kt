package com.example.popmate.model.repository.service

import com.example.popmate.model.data.remote.ExampleVO
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ExampleApiService {
    @POST("individual/{id}")
    fun getInfo(
        @Query("id") memberId: Int
    ): Call<ExampleVO>
}