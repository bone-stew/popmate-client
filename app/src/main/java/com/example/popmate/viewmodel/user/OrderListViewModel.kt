package com.example.popmate.viewmodel.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.user.OrderListItemsResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListViewModel : ViewModel() {
    private val orderListItem: MutableLiveData<OrderListItemsResponse> by lazy {
        MutableLiveData<OrderListItemsResponse>().also {
            loadOrderListItem()
        }
    }

    fun getOrderListItems() : LiveData<OrderListItemsResponse>{
        return orderListItem
    }

    private fun loadOrderListItem() {
        ApiClient.orderService.getOrderListItems().enqueue(object :
        Callback<ApiResponse<OrderListItemsResponse>>{
            override fun onResponse(
                call: Call<ApiResponse<OrderListItemsResponse>>,
                response: Response<ApiResponse<OrderListItemsResponse>>
            ) {
                Log.d("jjr", response.body()?.data?.orderListItemResponses.toString())
                orderListItem.value = response.body()?.data
            }

            override fun onFailure(call: Call<ApiResponse<OrderListItemsResponse>>, t: Throwable) {
                Log.e("jjr", "API 요청 실패", t)
                Log.e("jjr", "fdfdf")
            }

        })
    }

}