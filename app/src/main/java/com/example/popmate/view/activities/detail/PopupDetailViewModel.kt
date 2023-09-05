package com.example.popmate.view.activities.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.repository.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopupDetailViewModel : ViewModel() {
    private val store: MutableLiveData<PopupStore> by lazy {
        MutableLiveData<PopupStore>().also {
            loadStore()
        }
    }

    private val recommendStore: MutableLiveData<List<PopupStore>> by lazy {
        MutableLiveData<List<PopupStore>>().also {
            loadRecommendStore()
        }
    }

    fun getStore(): LiveData<PopupStore> {
        return store
    }

    fun getRecommendStore(): LiveData<List<PopupStore>> {
        return recommendStore
    }
    private fun loadStore() {
        ApiClient.storeService.getStoreDetail().enqueue(object : Callback<ApiResponse<PopupStore>>{
            override fun onResponse(call: Call<ApiResponse<PopupStore>>, response: Response<ApiResponse<PopupStore>>) {
                Log.d("kww", "onResponse: " + response.body())
                store.value = response.body()?.data
            }
            override fun onFailure(call: Call<ApiResponse<PopupStore>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun loadRecommendStore() {
    }

}