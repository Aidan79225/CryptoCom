package com.aidan.crypto.di

import androidx.room.Room
import com.aidan.crypto.database.CryptoDatabase
import com.aidan.crypto.domain.LoadDataFromAssetUseCase
import com.aidan.crypto.domain.SearchCurrencyInfoUseCase
import com.aidan.crypto.ui.currency.CurrencyListViewModel
import com.aidan.crypto.ui.demo.DemoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
val domainModule = module {
    factory { LoadDataFromAssetUseCase(get()) }
    factory { SearchCurrencyInfoUseCase() }
}

val appModule = module {
    viewModelOf(::DemoViewModel)
    viewModelOf(::CurrencyListViewModel)
}

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CryptoDatabase::class.java, "main.db"
        ).build()
    }
    single { get<CryptoDatabase>().currencyInfoDao() }
}