package com.example.popmate.model.data.local

import android.os.Bundle

data class Chat(
    val id: String?,
    val sender: Long?,
    val name: String?,
    val message: String,
    val roomId: Long,
    val createdAt: String?
) {

    constructor(message: String, roomId: Long) : this(null, null, null, message, roomId, null);
}
