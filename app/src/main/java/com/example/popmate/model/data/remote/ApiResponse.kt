package com.example.popmate.model.data.remote

data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T?
)
