package com.example.popmate.viewmodel.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.order.OrderListDetailResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderListDetailViewModel : ViewModel() {
    private val _orderDetailItem : MutableLiveData<OrderListDetailResponse> = MutableLiveData<OrderListDetailResponse>()
    val orderDetailItem : LiveData<OrderListDetailResponse> = _orderDetailItem


    fun loadDetails(orderId: Long) {
        ApiClient.orderService.getOrderListDetails(orderId).enqueue(object :
        Callback<ApiResponse<OrderListDetailResponse>>{
            override fun onResponse(
                call: Call<ApiResponse<OrderListDetailResponse>>,
                response: Response<ApiResponse<OrderListDetailResponse>>
            ) {
                Log.d("jjrd", response.body()?.data.toString())
                _orderDetailItem.value = response.body()?.data!!
            }

            override fun onFailure(call: Call<ApiResponse<OrderListDetailResponse>>, t: Throwable) {
                Log.e("jjrd", "API 요청 실패", t)
            }

        })
    }

}