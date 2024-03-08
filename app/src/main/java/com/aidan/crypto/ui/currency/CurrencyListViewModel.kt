package com.aidan.crypto.ui.currency

import androidx.lifecycle.ViewModel
import com.aidan.crypto.entity.CurrencyInfo
import com.aidan.crypto.domain.SearchCurrencyInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class CurrencyListViewModel(
    private val searchCurrencyInfoUseCase: SearchCurrencyInfoUseCase
) : ViewModel() {

    private val searchKeyFlow = MutableStateFlow("")
    private val totalCurrencyInfoFlow = MutableStateFlow(emptyList<CurrencyInfo>())

    val viewStateFlow = combine(
        searchKeyFlow,
        totalCurrencyInfoFlow
    ) { key, totalCurrencyInfoList ->
        ViewState(
            key,
            searchCurrencyInfoUseCase.execute(
                key,
                totalCurrencyInfoList
            )
        )
    }.flowOn(Dispatchers.Default)

    fun loadData(data: Array<CurrencyInfo>) {
        totalCurrencyInfoFlow.value = data.toList()
    }

    fun updateSearchKey(newKey: String) {
        searchKeyFlow.value = newKey
    }

    data class ViewState(
        val searchKey: String,
        val currencyInfoList: List<CurrencyInfo>
    )
}