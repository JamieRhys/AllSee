package com.sycosoft.allsee.presentation.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sycosoft.allsee.presentation.components.screens.homepage.HomePageScreen
import com.sycosoft.allsee.presentation.viewmodels.HomePageViewModel

@Composable
fun HomePage(
    viewModel: HomePageViewModel,
    onBalanceCardClick: () -> Unit,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    HomePageScreen(
        accountName = viewState.accountName,
        clearedBalance = viewState.clearedBalance,
        onPersonButtonClick = {},
        onBalanceCardClick = remember { onBalanceCardClick },
    )
}