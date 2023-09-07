package com.example.popmate.model.data.remote

data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T?
)

fun <T> Result<ApiResponse<T>>.getResult(): Result<T>? {
    this.onSuccess { response ->
        val data = response.data ?: return null
        return Result.success(data)
    }.onFailure { throwable ->
        return Result.failure(throwable)
    }
    return Result.failure(IllegalStateException("알 수 없는 오류가 발생했습니다."))
}
