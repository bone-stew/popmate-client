package com.example.popmate.model.repository.service.reservation

import com.example.popmate.model.data.remote.reservation.MyReservationDetailResponse
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.reservation.MyReservationsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ReservationApiService {

    @GET("/api/v1/members/me/reservations")
    fun getMyReservations(
    ): Call<ApiResponse<MyReservationsResponse>>

    @GET("members/me/reservations/{reservationId}")
    fun getMyReservation(
        @Path("reservationId") reservationId: Long
    ): Call<ApiResponse<MyReservationDetailResponse>>
}
