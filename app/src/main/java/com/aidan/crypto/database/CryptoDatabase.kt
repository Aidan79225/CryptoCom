package com.aidan.crypto.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aidan.crypto.entity.CurrencyInfo

@Database(
    version = 1,
    entities = [
        CurrencyInfo::class
    ]
)
abstract class CryptoDatabase: RoomDatabase() {
    abstract fun currencyInfoDao(): CurrencyInfoDao
}