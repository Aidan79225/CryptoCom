package com.aidan.crypto.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CurrencyInfo.TABLE_NAME)
data class CurrencyInfo(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String,
    val code: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(symbol)
        parcel.writeString(code)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrencyInfo> {
        const val TABLE_NAME = "CurrencyInfo"
        override fun createFromParcel(parcel: Parcel): CurrencyInfo {
            return CurrencyInfo(parcel)
        }

        override fun newArray(size: Int): Array<CurrencyInfo?> {
            return arrayOfNulls(size)
        }
    }
}