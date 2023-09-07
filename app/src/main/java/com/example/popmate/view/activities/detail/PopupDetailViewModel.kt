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
    private var userId = 1L
    private var popupStoreId: Long? = null
    private val store: MutableLiveData<PopupStore> by lazy {
        MutableLiveData<PopupStore>().also {
            loadStore()
        }
    }

    fun getStore(popupstoreId: Long): LiveData<PopupStore> {
        popupStoreId = popupstoreId

        return store
    }

    fun saveToPrefs() {
//        addToViewedRecently(store.value)
        Log.i("SEARCHRECENT", "Save to PREFS" + store.value.toString())

    }

    private fun addToViewedRecently(store: PopupStore?) {
        var storeList =  PopmateApplication.prefs.getList()
        Log.i("SEARCHRECENT", "ADDING TO LIST" + storeList.toString())
        Log.i("SEARCHRECENT", "ADDING THE STORE" + store.toString())
        var storeLinkedList: LinkedList<PopupStore>? = null
        if (storeList == null){
            storeLinkedList = LinkedList<PopupStore>()
        } else{
            storeLinkedList = LinkedList(storeList)
        }
        if (storeLinkedList.contains(store)){
            storeLinkedList.remove(store)
        }
            storeLinkedList.addFirst(store)
        if (storeLinkedList.size > 5) {
            storeLinkedList.removeLast()
        }
        PopmateApplication.prefs.setList("popmate", storeLinkedList.toList())
    }

    private fun loadStore() {
        ApiClient.storeService.getStoreDetail(popupStoreId!!, userId).enqueue(object : Callback<ApiResponse<PopupStore>>{
            override fun onResponse(call: Call<ApiResponse<PopupStore>>, response: Response<ApiResponse<PopupStore>>) {
                Log.d("kww", "onResponse: " + response.body())
                Log.d("kww", "onResponse: " + response)
                store.value = response.body()?.data
            }
            override fun onFailure(call: Call<ApiResponse<PopupStore>>, t: Throwable) {
            }
        })
    }

    private fun loadRecommendStore() {
    }

}
