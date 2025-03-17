package uk.co.jaffakree.allsee.feature_home.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.co.jaffakree.allsee.feature_home.screen.HomePageScreen
import uk.co.jaffakree.allsee.feature_home.viewmodel.HomePageViewModel

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