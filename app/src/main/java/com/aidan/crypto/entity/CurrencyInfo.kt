package com.aidan.crypto.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = CurrencyInfo.TABLE_NAME)
data class CurrencyInfo(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String,
    val code: String?
): Parcelable {

    companion object {
        const val TABLE_NAME = "CurrencyInfo"
    }
}