package com.example.popmate.model.repository.service.login

import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.login.GoogleLoginVO
import com.example.popmate.model.data.remote.login.LoginTokenVO
import com.example.popmate.model.data.remote.user.UserInformationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {
    @POST("oauth/kakao")
    fun getKakaoToken(
        @Body token: String
    ): Call<ApiResponse<LoginTokenVO>>

    @POST("oauth/google")
    fun getGoogleToken(
        @Body googleLoginVO: GoogleLoginVO
    ): Call<ApiResponse<LoginTokenVO>>

    @GET("oauth/resource")
    fun loginTest() : Call<LoginTokenVO>

    @GET("users/me")
    fun getUserName() : Call<ApiResponse<UserInformationResponse>>
}