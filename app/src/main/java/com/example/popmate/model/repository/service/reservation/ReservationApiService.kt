package com.example.popmate.model.repository.service.reservation

import com.example.popmate.model.data.remote.reservation.MyReservationDetailResponse
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.reservation.CurrentReservationResponse
import com.example.popmate.model.data.remote.reservation.MyReservationsResponse
import com.example.popmate.model.data.remote.reservation.ReservationRequest
import com.example.popmate.model.data.remote.reservation.ReservationSuccessResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationApiService {

    /**
     * 나의 예약 목록 조회
     */
    @GET("members/me/reservations")
    fun getMyReservations(
    ): Call<ApiResponse<MyReservationsResponse>>

    /**
     * 나의 예약 상세 조회
     */
    @GET("members/me/user-reservations/{userReservationId}")
    fun getMyReservation(
        @Path("userReservationId") userReservationId: Long
    ): Call<ApiResponse<MyReservationDetailResponse>>

    /**
     * 진행 중인 예약 조회
     */
    @GET("popup-stores/{popupStoreId}/current-reservations")
    fun getCurrentReservation(
        @Path("popupStoreId") popupStoreId: Long
    ): Call<ApiResponse<CurrentReservationResponse>>

    /**
     * 선착순 예약
     */
    @POST("reservations/{reservationId}")
    fun reserve(
        @Path("reservationId") reservationId: Long,
        @Body body: ReservationRequest
    ): Call<ApiResponse<ReservationSuccessResponse>>

    /**
     * 예약 취소
     */
    @PATCH("user-reservations/{userReservationId}/cancel")
    fun cancelReservation(
        @Path("userReservationId") userReservationId: Long
    ): Call<ApiResponse<Void>>
}
