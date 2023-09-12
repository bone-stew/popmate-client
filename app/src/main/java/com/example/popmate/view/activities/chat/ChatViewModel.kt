package com.example.popmate.view.activities.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popmate.config.BaseViewModel
import com.example.popmate.model.data.local.Chat
import com.example.popmate.model.data.local.CurrUser
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.chat.MessagesResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel : BaseViewModel() {

    private val _currUser: MutableLiveData<CurrUser> = MutableLiveData<CurrUser>()

    private val _chatList: MutableLiveData<List<Chat>> = MutableLiveData<List<Chat>>()

    var currUser: LiveData<CurrUser> = _currUser

    val chatList: LiveData<List<Chat>> = _chatList

    fun addChat(chat: Chat) {
        _chatList.postValue(_chatList.value?.plus(chat))
    }

    fun enterRoom(roomId: Long) {
        ApiClient.chatService.enter(roomId).enqueue(object : Callback<ApiResponse<String>> {
            override fun onResponse(
                call: Call<ApiResponse<String>>, response: Response<ApiResponse<String>>
            ) {
                response.body()?.message?.let { Log.d("kww", it) }
            }

            override fun onFailure(call: Call<ApiResponse<String>>, t: Throwable) {
                t.message?.let { Log.d("kww", it) }
            }

        })
    }

    fun loadChatMessage(roomId: Long) {
        ApiClient.chatService.loadMessages(roomId)
            .enqueue(object : Callback<ApiResponse<MessagesResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<MessagesResponse>>,
                    response: Response<ApiResponse<MessagesResponse>>
                ) {
                    _currUser.postValue(response.body()?.data?.currUser)
                    _chatList.postValue(response.body()?.data?.messages ?: emptyList())
                    Log.d("kww", "onResponse: ${response.body()?.data}")
                }

                override fun onFailure(call: Call<ApiResponse<MessagesResponse>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}
