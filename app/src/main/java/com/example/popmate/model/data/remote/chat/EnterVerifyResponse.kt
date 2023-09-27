package com.example.popmate.model.data.remote.chat

import java.time.LocalDateTime

data class EnterVerifyResponse(

    val denied: Boolean,
    val until: String?
)