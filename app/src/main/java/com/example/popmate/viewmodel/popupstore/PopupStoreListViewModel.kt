package com.example.popmate.viewmodel.popupstore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.popupstore.SearchResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopupStoreListViewModel: ViewModel() {



    private val _storeList: MutableLiveData<SearchResponse> = MutableLiveData<SearchResponse>()
    val storeList : LiveData<SearchResponse> = _storeList

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<Boolean> = MutableLiveData()
    val error: LiveData<Boolean> = _error

    fun clearList() {
        _storeList.value =  SearchResponse(emptyList())
    }


    fun loadList(
        isOpeningSoon: Boolean?,
        startDate: String?,
        endDate: String?,
        keyword: String?,
        offSetRows: Int?,
        rowsToGet: Int?
    ) {
        _loading.value = true
        _error.value = false

        ApiClient.storeService.getStoreSearch(
            isOpeningSoon,
            startDate,
            endDate,
            keyword,
            offSetRows,
            rowsToGet
        ).enqueue(object : Callback<ApiResponse<SearchResponse>> {
            override fun onResponse(call: Call<ApiResponse<SearchResponse>>, response: Response<ApiResponse<SearchResponse>>) {
                _loading.value = false
                if (response.isSuccessful){
                    _storeList.value = response.body()?.data!!
                } else {
                    _error.value = true
                }

                Log.i("HELLO", _storeList.value.toString())
            }
            override fun onFailure(call: Call<ApiResponse<SearchResponse>>, t: Throwable) {
                _loading.value = false
                _error.value = true
            }
        })
    }

}
