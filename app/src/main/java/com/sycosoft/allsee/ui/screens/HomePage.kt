package com.sycosoft.allsee.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.sycosoft.allsee.ui.viewmodels.HomePageViewModel

@Composable
fun HomePage(
    viewModel: HomePageViewModel = HomePageViewModel()
) {
    val account by viewModel.account.collectAsState()

    Scaffold() { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Text(account)
        }
    }
}