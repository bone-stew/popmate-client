package com.example.popmate.model.repository.service.chat

import com.example.popmate.model.data.local.Chat
import com.example.popmate.model.data.local.CurrUser
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.chat.CurrUserResponse
import com.example.popmate.model.data.remote.chat.EnterVerifyResponse
import com.example.popmate.model.data.remote.chat.MessagesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatApiService {

    @GET("chat/room/messages/{roomId}")
    fun loadMessages(
        @Path("roomId") roomId: Long,
        @Query("page") pageNum: Int,
        @Query("size") size: Int = 20,
        @Query("sort") sort: String = "createdAt,DESC"
    ): Call<ApiResponse<MessagesResponse>>

    @GET("chat/enter/{roomId}")
    fun enter(
        @Path("roomId") roomId: Long
    ): Call<ApiResponse<CurrUserResponse>>

    @GET("chat/thumbnail/{roomId}")
    fun getThumbnail(
        @Path("roomId") roomId: Long
    ): Call<ApiResponse<MessagesResponse>>

    @POST("chat/report")
    fun report(
        @Body chat: Chat
    ): Call<ApiResponse<String>>

    @GET("chat/enter-verify")
    fun enterVerify(): Call<ApiResponse<EnterVerifyResponse>>
}
