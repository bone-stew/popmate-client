package com.example.popmate.model.data.remote.popupstore

import com.example.popmate.model.data.local.PopupStore

data class SearchResponse(
    val popupStores: List<PopupStore>,
) {
    companion object {
        fun from(
            popupStores: List<PopupStore>
        ): SearchResponse {
            return SearchResponse(
                popupStores
            )
        }
    }
}
