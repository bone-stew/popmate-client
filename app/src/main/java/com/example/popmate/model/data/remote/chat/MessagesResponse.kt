package com.example.popmate.model.data.remote.chat

import com.example.popmate.model.data.local.Chat

data class MessagesResponse(
    val userId: Long,
    val messages: List<Chat>
)
