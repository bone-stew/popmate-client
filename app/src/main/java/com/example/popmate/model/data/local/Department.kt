package com.example.popmate.model.data.local

data class Department (
    val departmentId: Long,
    val name: String,
    val placeDescription: String,
    val latitude:  Double,
    val longitude: Double,
    val openTime:  String,
    val closeTime: String
)
