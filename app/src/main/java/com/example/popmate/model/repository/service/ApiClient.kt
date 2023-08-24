package com.example.popmate.model.repository.service


import com.example.popmate.model.repository.ExampleApiClient
import com.example.popmate.model.repository.service.login.LoginApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080/api/v1/popup-stores/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getTokenService: LoginApiService by lazy {
        ApiClient.retrofit.create(LoginApiService::class.java)
    }

}