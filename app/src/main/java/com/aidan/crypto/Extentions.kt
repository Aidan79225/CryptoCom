package com.aidan.crypto

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

fun <T> Activity.viewBinding(initializer: (inflater: LayoutInflater) -> T): Lazy<T> = lazy {
    initializer(this.layoutInflater)
}

fun <T> AppCompatActivity.subscribe(shareFlow: SharedFlow<T>, collector: FlowCollector<T>) {
    lifecycleScope.launch {
        shareFlow.collect(collector)
    }
}