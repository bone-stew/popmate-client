package com.example.popmate.viewmodel.popupstore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.popupstore.HomeResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel(){

    private val userId: Long = 1L

    private val home: MutableLiveData<HomeResponse> by lazy {
        MutableLiveData<HomeResponse>().also {
            loadHome()
        }
    }

    fun getHome(): LiveData<HomeResponse> {
        return home
    }

    fun refreshHome() {
        loadHome()
    }

    private fun loadHome() {
        ApiClient.storeService.getStoreHome(userId).enqueue(object : Callback<ApiResponse<HomeResponse>> {
            override fun onResponse(call: Call<ApiResponse<HomeResponse>>, response: Response<ApiResponse<HomeResponse>>) {
                Log.d("HOMEFRAGMENT", "onResponse: " + response.body())
                home.value = response.body()?.data
            }
            override fun onFailure(call: Call<ApiResponse<HomeResponse>>, t: Throwable) {
            }
        })
    }


}
