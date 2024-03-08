package com.aidan.crypto.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aidan.crypto.entity.CurrencyInfo

@Dao
interface CurrencyInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencyInfoList: List<CurrencyInfo>)

    @Query("SELECT * FROM ${CurrencyInfo.TABLE_NAME} WHERE code IS NULL")
    suspend fun getCryptoList(): List<CurrencyInfo>

    @Query("SELECT * FROM ${CurrencyInfo.TABLE_NAME} WHERE code IS NOT NULL")
    suspend fun getFiatList(): List<CurrencyInfo>

    @Query("SELECT * FROM ${CurrencyInfo.TABLE_NAME}")
    suspend fun getAllList(): List<CurrencyInfo>

    @Query("DELETE FROM ${CurrencyInfo.TABLE_NAME}")
    suspend fun deleteAll()
}