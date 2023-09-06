package com.example.popmate.model.repository.service.reservation

import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.reservation.MyReservationsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ReservationApiService {

    @GET("/api/v1/members/me/reservations")
    fun getMyReservations(
    ): Call<ApiResponse<MyReservationsResponse>>
}
