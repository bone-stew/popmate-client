package com.example.popmate.view.activities.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popmate.config.BaseViewModel
import com.example.popmate.model.data.local.Chat
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel : BaseViewModel() {

    private val chatList: MutableLiveData<List<Chat>> =
        MutableLiveData<List<Chat>>().apply { loadChatMessage(3) }

    fun getChatList(): LiveData<List<Chat>> {
        return chatList
    }

    fun addChat(chat: Chat) {
        chatList.postValue(chatList.value?.plus(chat))
    }

    private fun loadChatMessage(roomId: Long) {
        ApiClient.chatService.loadMessages(roomId).enqueue(object : Callback<ApiResponse<List<Chat>>>{
            override fun onResponse(
                call: Call<ApiResponse<List<Chat>>>,
                response: Response<ApiResponse<List<Chat>>>
            ) {
                chatList.postValue(response.body()?.data ?: emptyList())
                Log.d("kww", "onResponse: ${response.body()?.data}")
            }

            override fun onFailure(call: Call<ApiResponse<List<Chat>>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}
