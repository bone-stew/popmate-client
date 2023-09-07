package com.example.popmate.model.repository.service.chat

import com.example.popmate.model.data.local.Chat
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.chat.MessagesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatApiService {

    @GET("chat/room/messages/{roomId}")
    fun loadMessages(
        @Path("roomId") roomId: Long
    ): Call<ApiResponse<MessagesResponse>>
}