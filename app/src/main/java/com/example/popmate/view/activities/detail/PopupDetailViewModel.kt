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

    private val _store: MutableLiveData<PopupStore> =
        MutableLiveData<PopupStore>()

    private val _chat: MutableLiveData<List<Chat>> =
        MutableLiveData()

    val store: LiveData<PopupStore> = _store
    val chat: LiveData<List<Chat>> = _chat

    private fun addToViewedRecently(store: PopupStore?) {
        var storeList = PopmateApplication.prefs.getList()
        Log.i("SEARCHRECENT", "ADDING TO LIST" + storeList.toString())
        Log.i("SEARCHRECENT", "ADDING THE STORE" + store.toString())
        var storeLinkedList: LinkedList<PopupStore>? = null
        if (storeList == null) {
            storeLinkedList = LinkedList<PopupStore>()
        } else {
            storeLinkedList = LinkedList(storeList)
        }
        if (storeLinkedList.contains(store)) {
            storeLinkedList.remove(store)
        }
        storeLinkedList.addFirst(store)
        if (storeLinkedList.size > 5) {
            storeLinkedList.removeLast()
        }
        PopmateApplication.prefs.setList("popmate", storeLinkedList.toList())
    }

    fun loadStore(popupStoreId: Long) {
        ApiClient.storeService.getStoreDetail(popupStoreId)
            .enqueue(object : Callback<ApiResponse<PopupStore>> {
                override fun onResponse(
                    call: Call<ApiResponse<PopupStore>>,
                    response: Response<ApiResponse<PopupStore>>
                ) {
                    Log.d("kww", "onResponse: " + response.body())
                    _store.value = response.body()?.data!!
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
