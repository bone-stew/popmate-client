package com.example.popmate.model.data.remote.user

import com.example.popmate.model.data.remote.order.PopupStoreItem
import android.os.Parcel
import android.os.Parcelable

data class OrderListItem(
    val createdAt: String,
    val orderItemId: Int,
    val order: Any?, // null 값을 허용하는 경우 Any?로 선언합니다.
    val totalQuantity: Int,
    val totalAmount: Int,
    val popupStoreItem: PopupStoreItem?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readValue(Any::class.java.classLoader), // 'order'를 읽어오는 방법
        parcel.readInt(),
        parcel.readInt(),
        parcel.readParcelable(PopupStoreItem::class.java.classLoader) // 'popupStoreItem'을 읽어오는 방법
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(createdAt)
        parcel.writeInt(orderItemId)
        parcel.writeValue(order) // 'order'를 쓰는 방법
        parcel.writeInt(totalQuantity)
        parcel.writeInt(totalAmount)
        parcel.writeParcelable(popupStoreItem, flags) // 'popupStoreItem'을 쓰는 방법
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderListItem> {
        override fun createFromParcel(parcel: Parcel): OrderListItem {
            return OrderListItem(parcel)
        }

        override fun newArray(size: Int): Array<OrderListItem?> {
            return arrayOfNulls(size)
        }
    }
}
