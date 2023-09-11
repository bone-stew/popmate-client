package com.example.popmate.viewmodel.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.order.PopupStoreItemsResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewModel : ViewModel(){
    private val popupStoreItem: MutableLiveData<PopupStoreItemsResponse> by lazy {
        MutableLiveData<PopupStoreItemsResponse>().also {
            loadOrderItem()
        }
    }


    fun getPopupStoreItems() : LiveData<PopupStoreItemsResponse>{
        return popupStoreItem
    }

    private fun loadOrderItem() {
        ApiClient.orderService.getPopupStoreItems(1).enqueue(object:
            Callback<ApiResponse<PopupStoreItemsResponse>> {
            override fun onResponse(
                call: Call<ApiResponse<PopupStoreItemsResponse>>,
                response: Response<ApiResponse<PopupStoreItemsResponse>>
            ) {
                Log.d("jjr", "onResponse: " + response.body()?.data?.popupStoreItemResponse.toString())

                popupStoreItem.value = response.body()?.data
            }

            override fun onFailure(call: Call<ApiResponse<PopupStoreItemsResponse>>, t: Throwable) {
                Log.e("jjr", "API 요청 실패", t)
                Log.e("jjr", "fdfdf")
            }

        })
    }
}