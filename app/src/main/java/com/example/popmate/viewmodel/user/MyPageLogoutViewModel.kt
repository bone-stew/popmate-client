package com.example.popmate.viewmodel.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popmate.config.BaseViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.user.UserInformationResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageLogoutViewModel : BaseViewModel() {
    private val _myName : MutableLiveData<UserInformationResponse> = MutableLiveData<UserInformationResponse>()
    val myName : LiveData<UserInformationResponse> = _myName

    fun loadUser(){
        ApiClient.getTokenService.getUserName().enqueue(object :
        Callback<ApiResponse<UserInformationResponse>>{
            override fun onResponse(
                call: Call<ApiResponse<UserInformationResponse>>,
                response: Response<ApiResponse<UserInformationResponse>>
            ) {
                Log.d("jjr", "onResponse: " + response.body()?.data?.userName.toString())
                _myName.value = response.body()?.data!!
            }

            override fun onFailure(call: Call<ApiResponse<UserInformationResponse>>, t: Throwable) {
                Log.d("jjr", "API 요청 실패", t)
            }

        })
    }
}
