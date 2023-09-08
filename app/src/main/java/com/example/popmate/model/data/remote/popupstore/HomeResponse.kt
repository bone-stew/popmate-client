package com.example.popmate.model.data.remote.popupstore

import com.example.popmate.model.data.local.Banner
import com.example.popmate.model.data.local.PopupStore

data class HomeResponse(
    val banners: List<Banner>,
    val popupStoresVisitedBy: List<PopupStore>,
    val popupStoresRecommend: List<PopupStore>,
    val popupStoresEndingSoon: List<PopupStore>
) {
    companion object {
        fun of(
            bannerList: List<Banner>,
            popupStoresVisitedByList: List<PopupStore>,
            popupStoresRecommendList: List<PopupStore>,
            popupStoresEndingSoonList: List<PopupStore>
        ): HomeResponse {
            return HomeResponse(
                bannerList,
                popupStoresVisitedByList,
                popupStoresRecommendList,
                popupStoresEndingSoonList
            )
        }
    }
}
