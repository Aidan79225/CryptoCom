package com.aidan.crypto.domain

import android.content.Context
import com.aidan.crypto.entity.CurrencyInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class LoadDataFromAssetUseCase(
    private val context: Context
) {
    suspend fun execute(): List<CurrencyInfo> = withContext(Dispatchers.Default) {
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("data.json")))
        val jsonString = buildString {
            bufferReader.lines().forEach {
                append(it)
            }
        }
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory()).build()
        val listType = Types.newParameterizedType(List::class.java, CurrencyInfo::class.java)
        val jsonAdapter = moshi.adapter<List<CurrencyInfo>>(listType)
        return@withContext try {
            jsonAdapter.fromJson(jsonString) ?: emptyList()
        } catch (t: Throwable) {
            emptyList()
        }
    }
}