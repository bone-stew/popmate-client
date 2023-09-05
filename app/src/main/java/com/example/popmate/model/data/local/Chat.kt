package com.example.popmate.model.data.local

data class Chat(
    val id: String,
    val sender: String,
    val message: String,
    val roomId: String,
    val createdAt: String
)
