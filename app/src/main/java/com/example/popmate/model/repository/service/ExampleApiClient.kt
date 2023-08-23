package com.example.popmate.model.repository

import com.example.popmate.model.repository.service.ExampleApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExampleApiClient {
    private const val BASE_URL = "https://fcm.googleapis.com/fcm/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ExampleApiClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val fbsapiService: ExampleApiService by lazy {
        retrofit.create(ExampleApiService::class.java)
    }
}