package com.aidan.crypto.ui.demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.aidan.crypto.R
import com.aidan.crypto.databinding.ActivityDemoBinding
import com.aidan.crypto.subscribe
import com.aidan.crypto.ui.currency.CurrencyListFragment
import com.aidan.crypto.viewBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class DemoActivity: AppCompatActivity() {
    private val vb by viewBinding(ActivityDemoBinding::inflate)
    private val vm by viewModel<DemoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        subscribe(vm.viewState) {
            supportFragmentManager.beginTransaction().replace(
                vb.fragmentContainer.id,
                CurrencyListFragment.newInstance(it.currencyInfoList.toTypedArray())
            ).commit()
            setTitle(it.titleResId)
        }

        subscribe(vm.viewEvent) {
            if (it is DemoViewModel.ViewEvent.ShowToast) {
                Toast.makeText(this, it.msgResId, Toast.LENGTH_SHORT).show()
            }
        }
        vm.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_demo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.clear_data -> {
                vm.deleteAll()
            }
            R.id.load_data -> {
                vm.loadDataFromAssets(this)
            }
            R.id.use_crypto -> {
                vm.loadCryptoCurrency()
            }
            R.id.use_fiat -> {
                vm.loadFiatCurrency()
            }
            R.id.use_all -> {
                vm.loadAllCurrency()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}