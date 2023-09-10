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

    private val _home: MutableLiveData<HomeResponse> = MutableLiveData<HomeResponse>()

    val home: LiveData<HomeResponse> = _home


    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<Boolean> = MutableLiveData()
    val error: LiveData<Boolean> = _error


    fun loadHome() {
        _loading.value = true
        _error.value = false
        ApiClient.storeService.getStoreHome(userId).enqueue(object : Callback<ApiResponse<HomeResponse>> {
            override fun onResponse(call: Call<ApiResponse<HomeResponse>>, response: Response<ApiResponse<HomeResponse>>) {
                Log.d("HOMEFRAGMENT", "onResponse: " + response.body())
                _loading.value = false
                if (response.isSuccessful){
                _home.value = response.body()?.data
                } else {
                    _error.value = true
                }
            }
            override fun onFailure(call: Call<ApiResponse<HomeResponse>>, t: Throwable) {
                Log.d("kww", "onFailure: ")
                _loading.value = false
                _error.value = true
            }
        })
    }


}
