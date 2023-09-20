package com.example.popmate.model.data.remote.user

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Orders(
    val createdAt: String,
    val orderId: Int,
    val userId: Int,
    val popupStoreId: Int,
    val title: String,
    val placeDetail: String,
    val bannerImgUrl: String,
    val total_amount: Int,
    val status: Int,
    val orderTossId: String,
    val url: String,
    val cardType: String,
    val easyPay: Any?,
    val method: String,
    val qrImgUrl: String,
    val orderItemList: List<OrderListItem>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readSerializable() as Any?,
        parcel.readString() ?: "",
        parcel.readString() ?:"",
        parcel.createTypedArrayList(OrderListItem.CREATOR) ?: ArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(createdAt)
        parcel.writeInt(orderId)
        parcel.writeInt(userId)
        parcel.writeInt(popupStoreId)
        parcel.writeString(title)
        parcel.writeString(placeDetail)
        parcel.writeString(bannerImgUrl)
        parcel.writeInt(total_amount)
        parcel.writeInt(status)
        parcel.writeString(orderTossId)
        parcel.writeString(url)
        parcel.writeString(cardType)
        parcel.writeSerializable(easyPay as Serializable?)
        parcel.writeString(method)
        parcel.writeString(qrImgUrl)
        parcel.writeTypedList(orderItemList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Orders> {
        override fun createFromParcel(parcel: Parcel): Orders {
            return Orders(parcel)
        }

        override fun newArray(size: Int): Array<Orders?> {
            return arrayOfNulls(size)
        }
    }
}
