package com.sycosoft.allsee.presentation.pages

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.sycosoft.allsee.presentation.components.screens.homepage.HomePageScreen
import com.sycosoft.allsee.presentation.viewmodels.HomePageViewModel

@Composable
fun HomePage(viewModel: HomePageViewModel) {
    val personDetails = viewModel.personDetails.collectAsState()

    Scaffold { innerPadding ->
        HomePageScreen(
            innerPadding = innerPadding,
            accountName = personDetails.value?.type?.displayName,
            onPersonButtonClick = {},
        )
    }
}