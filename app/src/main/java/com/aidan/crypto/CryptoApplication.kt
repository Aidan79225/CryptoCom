package com.aidan.crypto

import android.app.Application
import com.aidan.crypto.di.appModule
import com.aidan.crypto.di.dbModule
import com.aidan.crypto.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CryptoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoApplication)
            modules(domainModule, appModule, dbModule)
        }
    }
}