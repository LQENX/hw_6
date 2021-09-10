package com.mycompany.hw_5

import android.os.Parcel
import android.os.Parcelable

data class ContactItem(
    var name: String,
    var surname: String,
    var phoneNumber: String,
    var image: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString()?: "",
            parcel.readString()?: "",
            parcel.readString()?: "",
            parcel.readString()?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(phoneNumber)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactItem> {
        override fun createFromParcel(parcel: Parcel): ContactItem {
            return ContactItem(parcel)
        }

        override fun newArray(size: Int): Array<ContactItem?> {
            return arrayOfNulls(size)
        }
    }
}