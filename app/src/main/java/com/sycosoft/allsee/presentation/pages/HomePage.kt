package com.sycosoft.allsee.presentation.pages

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.sycosoft.allsee.presentation.components.homepage.HomePageScreen
import com.sycosoft.allsee.presentation.viewmodels.HomePageViewModel

@Composable
fun HomePage(viewModel: HomePageViewModel) {
    Scaffold { innerPadding ->
        HomePageScreen(
            innerPadding = innerPadding,
        )
    }
}