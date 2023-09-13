package com.example.popmate.model.data.remote.order

import android.os.Parcel
import android.os.Parcelable

data class OrderPlaceDetailResponse(
    val title: String,
    val placeDetail: String,
    val placeDescription: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(placeDetail)
        parcel.writeString(placeDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderPlaceDetailResponse> {
        override fun createFromParcel(parcel: Parcel): OrderPlaceDetailResponse {
            return OrderPlaceDetailResponse(parcel)
        }

        override fun newArray(size: Int): Array<OrderPlaceDetailResponse?> {
            return arrayOfNulls(size)
        }
    }
}

