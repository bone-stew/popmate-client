package com.example.popmate.view.activities.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.config.PopmateApplication
import com.example.popmate.model.data.local.Chat
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.chat.MessagesResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.LinkedList

class PopupDetailViewModel() : ViewModel() {

    private val _chat: MutableLiveData<List<Chat>> =
        MutableLiveData()
    val chat: LiveData<List<Chat>> = _chat
  
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
                    Log.i("HELLO",response.body()?.data!!.toString() )
                    _loading.value = false
                    _store.value = response.body()?.data!!
                    _status.value = response.body()?.data!!.status == 1
                }

                override fun onFailure(call: Call<ApiResponse<PopupStore>>, t: Throwable) {
                    Log.d("kww", "onFailure: ${t.message}")
                }
            })
    }

    fun loadChatThumbnail(popupStoreId: Long) {
        ApiClient.chatService.getThumbnail(popupStoreId)
            .enqueue(object: Callback<ApiResponse<MessagesResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<MessagesResponse>>,
                    response: Response<ApiResponse<MessagesResponse>>
                ) {
                    Log.d("kww", "onResponse: ${response.body()?.data?.messages}")
                    _chat.postValue(response.body()?.data?.messages)
                }

                override fun onFailure(call: Call<ApiResponse<MessagesResponse>>, t: Throwable) {
                    Log.d("kww", "onFailure: ${t.message}")
                }

            })
    }
}
