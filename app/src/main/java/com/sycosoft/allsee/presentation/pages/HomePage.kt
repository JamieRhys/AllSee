package com.sycosoft.allsee.presentation.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sycosoft.allsee.presentation.components.screens.homepage.HomePageScreen
import com.sycosoft.allsee.presentation.usecases.FormatBalanceUseCase
import com.sycosoft.allsee.presentation.viewmodels.HomePageViewModel

@Composable
fun HomePage(viewModel: HomePageViewModel) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val formatBalanceUseCase = FormatBalanceUseCase()

    HomePageScreen(
        accountName = viewState.accountName,
        clearedBalance = formatBalanceUseCase(viewState.clearedBalance),
        onPersonButtonClick = {},
        onBalanceCardClick = {},
    )
}