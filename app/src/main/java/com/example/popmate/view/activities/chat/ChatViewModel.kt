package com.example.popmate.view.activities.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popmate.config.BaseViewModel
import com.example.popmate.model.data.local.Chat
import com.example.popmate.model.data.local.CurrUser
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.chat.CurrUserResponse
import com.example.popmate.model.data.remote.chat.MessagesResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel : BaseViewModel() {

    private val TAG = "ChatViewModel"

    private var pageNum = 0

    private val _currUser: MutableLiveData<CurrUser> = MutableLiveData<CurrUser>()

    private val _chatList: MutableLiveData<List<Chat>> = MutableLiveData<List<Chat>>()

    var currUser: LiveData<CurrUser> = _currUser

    val chatList: LiveData<List<Chat>> = _chatList

    var chatFullyLoaded = false

    fun addChat(chat: Chat) {
        val newList: MutableList<Chat> = ArrayList(_chatList.value ?: emptyList())
        newList.add(0, chat)
        _chatList.postValue(newList)
    }

    fun enterRoom(roomId: Long) {
        ApiClient.chatService.enter(roomId).enqueue(object : Callback<ApiResponse<CurrUserResponse>> {
            override fun onResponse(
                call: Call<ApiResponse<CurrUserResponse>>, response: Response<ApiResponse<CurrUserResponse>>
            ) {
                response.body()?.data?.let {
                    Log.d(TAG, "onResponse: $it")
                    _currUser.postValue(CurrUser(it.userId, it.nickname))
                    loadChatMessage(roomId)
                }
            }
            override fun onFailure(call: Call<ApiResponse<CurrUserResponse>>, t: Throwable) {
                t.message?.let { Log.d(TAG, it) }
            }
        })
    }

    fun loadChatMessage(roomId: Long) {
        if (chatFullyLoaded) return
        chatFullyLoaded = true
        ApiClient.chatService.loadMessages(roomId = roomId, pageNum = pageNum++)
            .enqueue(object : Callback<ApiResponse<MessagesResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<MessagesResponse>>,
                    response: Response<ApiResponse<MessagesResponse>>
                ) {
                    Log.d("kww", "onResponse: ${response.body()?.data}")
                    response.body()?.data?.let {
                        if (it.messages.isNotEmpty()) {
                            val newList: MutableList<Chat> = ArrayList(_chatList.value ?: emptyList())
                            newList.addAll(it.messages)
                            Log.d(TAG, "newList: ${newList}")
                            _chatList.postValue(newList)
                            chatFullyLoaded = false
                        }
                    }
                }

                override fun onFailure(call: Call<ApiResponse<MessagesResponse>>, t: Throwable) {
                    t.message?.let { Log.d("kww", it) }
                }
            })
    }
}
