package com.example.popmate.model.data.local

import android.os.Bundle

data class Chat(
    val id: String?,
    var sender: Long?,
    var name: String?,
    val message: String,
    val roomId: Long,
    val createdAt: String?
) {

    constructor(message: String, roomId: Long, sender: Long, name: String) : this(null, sender, name, message, roomId, null);
}
