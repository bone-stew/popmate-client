package com.example.popmate.model.repository.service.reservation

import com.example.popmate.model.data.remote.reservation.MyReservationDetailResponse
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.reservation.CurrentReservationResponse
import com.example.popmate.model.data.remote.reservation.MyReservationsResponse
import com.example.popmate.model.data.remote.reservation.ReservationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationApiService {

    @GET("members/me/reservations")
    fun getMyReservations(
    ): Call<ApiResponse<MyReservationsResponse>>

    @GET("members/me/reservations/{reservationId}")
    fun getMyReservation(
        @Path("reservationId") reservationId: Long
    ): Call<ApiResponse<MyReservationDetailResponse>>

    @GET("popup-stores/{popupStoreId}/current-reservations")
    fun getCurrentReservation(
        @Path("popupStoreId") popupStoreId: Long
    ): Call<ApiResponse<CurrentReservationResponse>>

    @POST("reservations/{reservationId}")
    fun reserve(
        @Path("reservationId") reservationId: Long,
        @Body body: ReservationRequest
    ): Call<ApiResponse<Void>>

    @PATCH("reservations/{reservationId}/cancel")
    fun cancelReservation(
        @Path("reservationId") reservationId: Long
    ): Call<ApiResponse<Void>>
}
