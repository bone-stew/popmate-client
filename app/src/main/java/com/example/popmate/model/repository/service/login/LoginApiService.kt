package com.example.popmate.model.repository.service.login

import com.example.popmate.model.data.remote.login.LoginTokenVO
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginApiService {
    @POST("oauth/{id}")
    fun getToken(
        @Path("id") token: String
    ): Call<LoginTokenVO>
}