package com.example.popmate.view.activities.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popmate.config.BaseViewModel
import com.example.popmate.model.data.local.Chat
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.chat.MessagesResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel : BaseViewModel() {

    private val _currUserId: MutableLiveData<Long> = MutableLiveData<Long>()

    private val _chatList: MutableLiveData<List<Chat>> = MutableLiveData<List<Chat>>()

    var currUserId: LiveData<Long> = _currUserId

    val chatList: LiveData<List<Chat>> = _chatList

    fun addChat(chat: Chat) {
        _chatList.postValue(_chatList.value?.plus(chat))
    }

    fun loadChatMessage(roomId: Long) {
        ApiClient.chatService.loadMessages(roomId)
            .enqueue(object : Callback<ApiResponse<MessagesResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<MessagesResponse>>,
                    response: Response<ApiResponse<MessagesResponse>>
                ) {
                    _currUserId.postValue(response.body()?.data?.userId)
                    _chatList.postValue(response.body()?.data?.messages ?: emptyList())
                    Log.d("kww", "onResponse: ${response.body()?.data}")
                }

                override fun onFailure(call: Call<ApiResponse<MessagesResponse>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}
