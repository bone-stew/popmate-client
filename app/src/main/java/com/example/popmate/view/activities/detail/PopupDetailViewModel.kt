package com.example.popmate.view.activities.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.config.PopmateApplication
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.LinkedList

class PopupDetailViewModel() : ViewModel() {
    private val _store: MutableLiveData<PopupStore> =
        MutableLiveData<PopupStore>()
    val store: LiveData<PopupStore> = _store

    private val _status: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading


    fun loadStore(popupStoreId: Long) {
        _loading.value = true
        ApiClient.storeService.getStoreDetail(popupStoreId)
            .enqueue(object : Callback<ApiResponse<PopupStore>> {
                override fun onResponse(
                    call: Call<ApiResponse<PopupStore>>,
                    response: Response<ApiResponse<PopupStore>>
                ) {
                    _loading.value = false
                    _store.value = response.body()?.data!!
                    _status.value = response.body()?.data!!.status == 1
                }

                override fun onFailure(call: Call<ApiResponse<PopupStore>>, t: Throwable) {
                }
            })
    }


}
