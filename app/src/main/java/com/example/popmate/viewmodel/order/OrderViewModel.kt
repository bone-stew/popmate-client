package com.example.popmate.viewmodel.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.order.PlaceDetailResponse
import com.example.popmate.model.data.remote.order.PopupStoreItemsResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewModel : ViewModel(){

    private val _popupStoreItem : MutableLiveData<PopupStoreItemsResponse> = MutableLiveData<PopupStoreItemsResponse>()
    val popupStoreItem : LiveData<PopupStoreItemsResponse> = _popupStoreItem

    private val _placeDetail : MutableLiveData<PlaceDetailResponse> = MutableLiveData<PlaceDetailResponse>()
    val placeDetail : LiveData<PlaceDetailResponse> = _placeDetail
    fun loadList(
        popupStoreId:Long?
    ){
            ApiClient.orderService.getPopupStoreItems(popupStoreId!!).enqueue(object:
                Callback<ApiResponse<PopupStoreItemsResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<PopupStoreItemsResponse>>,
                    response: Response<ApiResponse<PopupStoreItemsResponse>>
                ) {
                    Log.d("jjr", "onResponse: " + response.body()?.data?.popupStoreItemResponse.toString())

                    _popupStoreItem.value = response.body()?.data!!
                }

                override fun onFailure(call: Call<ApiResponse<PopupStoreItemsResponse>>, t: Throwable) {
                    Log.e("jjr", "API 요청 실패", t)
                }

            })
    }

//    fun loadPlaceDetail
//                (popupStoreId:Long?
//    ) {
//        ApiClient.orderService.getPlaceDetails(popupStoreId!!).enqueue(
//            object : Callback<ApiResponse<PlaceDetailResponse>>{
//
//            override fun onResponse(
//                call: Call<ApiResponse<PlaceDetailResponse>>,
//                response: Response<ApiResponse<PlaceDetailResponse>>
//            ) {
//                _placeDetail.value = response.body()?.data!!
//                Log.e("jjra", response.body()?.data.toString())
//            }
//
//            override fun onFailure(call: Call<ApiResponse<PlaceDetailResponse>>, t: Throwable) {
//                Log.e("jjra", "API 요청 실패", t)
//            }
//
//        })
//    }


}