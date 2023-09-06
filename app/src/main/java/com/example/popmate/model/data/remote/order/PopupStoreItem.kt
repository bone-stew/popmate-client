package com.example.popmate.model.data.remote.order

import android.os.Parcel
import android.os.Parcelable

data class PopupStoreItem(
    val itemId: Long,
    val name: String,
    val popupStoreId: Long,
    val imgUrl: String,
    val amount: Int,
    val stock: Int,
    val orderLimit: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(itemId)
        parcel.writeString(name)
        parcel.writeLong(popupStoreId)
        parcel.writeString(imgUrl)
        parcel.writeInt(amount)
        parcel.writeInt(stock)
        parcel.writeInt(orderLimit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PopupStoreItem> {
        override fun createFromParcel(parcel: Parcel): PopupStoreItem {
            return PopupStoreItem(parcel)
        }

        override fun newArray(size: Int): Array<PopupStoreItem?> {
            return arrayOfNulls(size)
        }
    }
}


