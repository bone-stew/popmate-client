package com.example.popmate.model.repository.service


import android.util.Log
import com.example.popmate.model.data.remote.login.LoginTokenVO
import com.example.popmate.model.repository.service.login.LoginApiService
import com.example.popmate.model.repository.service.popupStore.StoreApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object ApiClient {
//
//    private var jwtToken: String? = null
//
    private const val BASE_URL = "http://10.0.2.2:8080/api/v1/"
//
//    private val retrofit: Retrofit by lazy {
//
//        val httpClient = OkHttpClient.Builder()
//            .addInterceptor(object : Interceptor {
//                @Throws(IOException::class)
//                override fun intercept(chain: Interceptor.Chain): Response {
//                    val originalRequest: Request = chain.request()
//
//                    // JWT 토큰이 있는 경우 헤더에 추가
//                    val token = getJwtToken()
//                    val newRequest: Request = if (token != null) {
//                        originalRequest.newBuilder()
//                            .header("Authorization", "Bearer $token") // 헤더에 토큰 추가
//                            .build()
//                    } else {
//                        originalRequest
//                    }
//                    Log.i("TestActivity", "인터셉터를 통해 토큰 담김")
//                    Log.i("TestActivity", token.toString())
//                    return chain.proceed(newRequest)
//                }
//            })
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY // 요청 및 응답 로그 출력 레벨 설정
//            })
//            .build()
//
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create()) // Gson을 사용한 JSON 파싱 설정
//            .client(httpClient) // OkHttpClient 설정
//            .build() // Retrofit 인스턴스 생성
//    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getTokenService: LoginApiService by lazy {
        ApiClient.retrofit.create(LoginApiService::class.java)
    }

    val storeService: StoreApiService by lazy {
        retrofit.create(StoreApiService::class.java)
    }
//    fun setJwtToken(token: LoginTokenVO?) {
//        if (token != null) {
//            jwtToken = token.token
//        }
//    }
//
//    fun getJwtToken(): String? {
//        return jwtToken
//    }
//
//    fun loginCheck(): Boolean{
//        return jwtToken!=null
//    }
}