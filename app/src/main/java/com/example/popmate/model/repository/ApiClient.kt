package com.example.popmate.model.repository


import android.util.Log
import com.example.popmate.model.repository.service.chat.ChatApiService
import com.example.popmate.model.repository.service.login.LoginApiService
import com.example.popmate.model.repository.service.order.OrderApiService
import com.example.popmate.model.repository.service.popupStore.StoreApiService
import com.example.popmate.model.repository.service.reservation.ReservationApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object ApiClient {

    private var jwtToken: String? = null

//        private const val BASE_URL = "http://10.0.2.2:8080/api/v1/"
//    private const val BASE_URL = "http://192.168.0.64:8080/api/v1/"
    private const val BASE_URL = "http://192.168.0.51:8080/api/v1/"
//    private const val BASE_URL = "https://popmate.xyz/api/v1/"

    private val retrofit: Retrofit by lazy {

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalRequest: Request = chain.request()

                    // JWT 토큰이 있는 경우 헤더에 추가
                    val token = getJwtToken()
//                    val token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmcm9nczYyMjVAbmF2ZXIuY29tIiwidXNlcklkIjo0MiwidXNlck5hbWUiOiLsobDsnqzro6EiLCJpYXQiOjE2OTQ0MTczMTQsImV4cCI6MTY5OTY1NTQ1OH0.vzkIRAc3Vt3Cj4HXnbXyAoUt2EAy7qRxzttZDRQ_VAE"
                    val newRequest: Request = if (token != null) {
                        originalRequest.newBuilder()
                            .header("Authorization", "Bearer ${token}") // 헤더에 토큰 추가
                            .build()
                    } else {
                        originalRequest
                    }
                    Log.i("TestActivity", "인터셉터를 통해 토큰 담김")
                    Log.i("TestActivity", token.toString())
                    return chain.proceed(newRequest)
                }
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // 요청 및 응답 로그 출력 레벨 설정
            })
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Gson을 사용한 JSON 파싱 설정
            .client(httpClient) // OkHttpClient 설정
            .build() // Retrofit 인스턴스 생성
    }


    val getTokenService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }

    val orderService: OrderApiService by lazy {
        retrofit.create(OrderApiService::class.java)
    }

    val storeService: StoreApiService by lazy {
        retrofit.create(StoreApiService::class.java)
    }

    val chatService: ChatApiService by lazy {
        retrofit.create(ChatApiService::class.java)
    }

    val reservationService: ReservationApiService by lazy {
        retrofit.create(ReservationApiService::class.java)
    }


    fun setJwtToken(token: String?) {
        if (token != null) {
            jwtToken = token
        }else jwtToken = token
    }

    fun getJwtToken(): String? {
        return jwtToken
    }

    fun loginCheck(): Boolean{
        return jwtToken != null && jwtToken != ""
    }
}
