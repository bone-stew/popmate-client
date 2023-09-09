package com.example.popmate.viewmodel.popupstore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.model.data.remote.ApiResponse
import com.example.popmate.model.data.remote.popupstore.HomeResponse
import com.example.popmate.model.data.remote.popupstore.SearchResponse
import com.example.popmate.model.repository.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopupStoreListViewModel: ViewModel() {


    private val userId: Long = 1L

    private val _storeList: MutableLiveData<SearchResponse> = MutableLiveData<SearchResponse>()
    val storeList : LiveData<SearchResponse> = _storeList

    fun clearList() {
        _storeList.value =  SearchResponse(emptyList())
    }

    fun refreshList(
        isOpeningSoon: Boolean?,
        startDate: String?,
        endDate: String?,
        keyword: String?,
        offSetRows: Int?,
        rowsToGet: Int?
    ) {
        loadList(isOpeningSoon, startDate, endDate, keyword, offSetRows, rowsToGet)
    }

    fun loadList(
        isOpeningSoon: Boolean?,
        startDate: String?,
        endDate: String?,
        keyword: String?,
        offSetRows: Int?,
        rowsToGet: Int?
    ) {

        ApiClient.storeService.getStoreSearch(
            isOpeningSoon,
            startDate,
            endDate,
            keyword,
            offSetRows,
            rowsToGet
        ).enqueue(object : Callback<ApiResponse<SearchResponse>> {
            override fun onResponse(call: Call<ApiResponse<SearchResponse>>, response: Response<ApiResponse<SearchResponse>>) {
                _storeList.value = response.body()?.data
            }
            override fun onFailure(call: Call<ApiResponse<SearchResponse>>, t: Throwable) {

            }
        })
    }

}
