package com.aidan.crypto.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.aidan.crypto.R
import com.aidan.crypto.entity.CurrencyInfo
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyListFragment : Fragment() {
    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        fun newInstance(data: Array<CurrencyInfo>): CurrencyListFragment {
            return CurrencyListFragment().apply {
                arguments = bundleOf(
                    EXTRA_DATA to data
                )
            }
        }
    }

    private val vm by viewModel<CurrencyListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.loadData(arguments?.getParcelableArray(EXTRA_DATA) as? Array<CurrencyInfo> ?: arrayOf())
        val composeView = view as ComposeView
        composeView.setContent {
            CurrencyListScreen(
                vm
            )
        }
    }
}

@Composable
fun CurrencyListScreen(
    vm: CurrencyListViewModel
) {
    val viewState by vm.viewStateFlow.collectAsState(
        CurrencyListViewModel.ViewState(
            "",
            emptyList()
        )
    )
    Column(
        Modifier.fillMaxWidth()
    ) {
        SearchCurrencyBar(vm, viewState)
        if (viewState.currencyInfoList.isEmpty()) {
            CurrencyNotFoundPage()
        } else {
            CurrencyInfoListPage(viewState)
        }
    }
}

@Composable
fun SearchCurrencyBar(
    vm: CurrencyListViewModel,
    viewState: CurrencyListViewModel.ViewState
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(Color.LightGray)
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current
        BasicTextField(
            value = viewState.searchKey,
            onValueChange = { vm.updateSearchKey(it) },
            textStyle = TextStyle(
                fontSize = 20.sp,
                color = Color.DarkGray
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (viewState.searchKey.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.search_hint_text),
                        fontSize = 20.sp,
                    )
                }
                innerTextField()
            }
        )
        if (viewState.searchKey.isEmpty()) {
            Icon(Icons.Default.Search, contentDescription = "")
        } else {
            Icon(Icons.Default.Clear, contentDescription = "", modifier = Modifier.clickable {
                vm.updateSearchKey("")
                keyboardController?.hide()
                focusManager.clearFocus()
            })
        }
    }
}

@Composable
fun CurrencyNotFoundPage() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_not_found),
            contentDescription = "",
            modifier = Modifier
                .padding(0.dp, 30.dp, 0.dp, 0.dp)
                .height(160.dp)
                .width(160.dp)
        )
        Text(
            modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),
            text = stringResource(id = R.string.no_results),
            fontSize = 26.sp
        )

    }
}

@Composable
fun CurrencyInfoListPage(viewState: CurrencyListViewModel.ViewState) {
    LazyColumn {
        items(viewState.currencyInfoList) {
            CurrencyInfoRow(currencyInfo = it)
        }
    }
}

@Composable
fun CurrencyInfoRow(currencyInfo: CurrencyInfo) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Text(
            text = currencyInfo.name.first().toString(),
            color = Color.White,
            modifier = Modifier
                .padding(10.dp)
                .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                .padding(10.dp, 7.dp, 10.dp, 7.dp),
            fontSize = 14.sp
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = currencyInfo.name,
                    color = Color.Black,
                    fontSize = 14.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = (currencyInfo.code?.let { "$it " } ?: "") + currencyInfo.symbol,
                        color = Color.Black,
                        fontSize = 14.sp
                    )

                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "",
                        modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                    )

                }
            }
            HorizontalDivider(color = Color.LightGray)
        }
    }
}