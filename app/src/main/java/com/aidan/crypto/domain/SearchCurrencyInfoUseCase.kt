package com.aidan.crypto.domain

import com.aidan.crypto.entity.CurrencyInfo

class SearchCurrencyInfoUseCase {
    fun execute(key: String, totalCurrencyInfoList: List<CurrencyInfo>): List<CurrencyInfo> {
        val formalKey = key.lowercase()
        return totalCurrencyInfoList.filter {
            it.name.lowercase().startsWith(formalKey) ||
                    it.symbol.lowercase().startsWith(formalKey) ||
                    containsPartial(formalKey, it)
        }
    }

    private fun containsPartial(key: String, currencyInfo: CurrencyInfo): Boolean {
        return currencyInfo.name
            .lowercase()
            .split(" ")
            .firstOrNull { it.startsWith(key) } != null
    }
}