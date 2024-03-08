package com.aidan.crypto.ui.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aidan.crypto.R
import com.aidan.crypto.entity.CurrencyInfo
import com.aidan.crypto.database.CurrencyInfoDao
import com.aidan.crypto.domain.LoadDataFromAssetUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DemoViewModel(
    private val loadDataFromAssetUseCase: LoadDataFromAssetUseCase,
    private val currencyInfoDao: CurrencyInfoDao
): ViewModel() {

    private val _viewState = MutableStateFlow(ViewState(emptyList()))
    val viewState = _viewState.asStateFlow()

    private val _viewEvent = MutableSharedFlow<ViewEvent>()
    val viewEvent = _viewEvent.asSharedFlow()

    fun start() {
        loadCryptoCurrency()
    }

    fun deleteAll() {
        viewModelScope.launch {
            currencyInfoDao.deleteAll()
            _viewEvent.emit(ViewEvent.ShowToast(R.string.clear_data_succeed_msg))
        }
    }

    fun loadDataFromAssets() {
        viewModelScope.launch {
            val data = loadDataFromAssetUseCase.execute()
            currencyInfoDao.insertAll(data)
            _viewEvent.emit(ViewEvent.ShowToast(R.string.load_data_succeed_msg))
        }
    }

    fun loadCryptoCurrency() {
        updateViewState(R.string.load_crypto_data_succeed_msg) { currencyInfoDao.getCryptoList() }
    }

    fun loadFiatCurrency() {
        updateViewState(R.string.load_fiat_data_succeed_msg) { currencyInfoDao.getFiatList() }
    }

    fun loadAllCurrency() {
        updateViewState(R.string.load_all_data_succeed_msg) { currencyInfoDao.getAllList() }
    }

    private fun updateViewState(msgResId: Int, dataProvider: suspend () -> List<CurrencyInfo>) {
        viewModelScope.launch {
            val data = dataProvider()
            _viewState.value = ViewState(data)
            if (data.isNotEmpty()) {
                _viewEvent.emit(ViewEvent.ShowToast(msgResId))
            }
        }
    }

    data class ViewState(
        val currencyInfoList: List<CurrencyInfo>
    )

    sealed class ViewEvent {
        data class ShowToast(val msgResId: Int): ViewEvent()
    }
}