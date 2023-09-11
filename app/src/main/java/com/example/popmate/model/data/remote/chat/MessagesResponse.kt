package com.example.popmate.model.data.remote.chat

import com.example.popmate.model.data.local.Chat
import com.example.popmate.model.data.local.CurrUser

data class MessagesResponse(
    val currUser: CurrUser,
    val messages: List<Chat>
)
