package com.sycosoft.allsee.presentation.components.accountaccesspage

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sycosoft.allsee.R
import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.presentation.theme.AllSeeTheme
import com.sycosoft.allsee.presentation.utils.UiState

@Composable
fun AccessTokenRequestScreen(
    topSectionColor: Color = MaterialTheme.colorScheme.inversePrimary,
    bottomSectionColor: Color = MaterialTheme.colorScheme.background,
    accessToken: String,
    onAccessTokenChange: (String) -> Unit,
    onGetStartedButtonClick: () -> Unit,
    uiState: UiState<NameAndAccountType>,
    errorSnackBarCallback: @Composable (String) -> Unit,
    onOpenConfirmationDialog: (Boolean) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("screen_access_token_request")
    ) {
        // Background/TopRight
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.60f)
                .align(Alignment.TopEnd)
                .background(bottomSectionColor)
        )
        // Background/Top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .align(Alignment.TopCenter)
                .background(
                    color = topSectionColor,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 90.dp
                    )
                )
        )
        // Background/BottomLeft
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.40f)
                .align(Alignment.BottomStart)
                .background(topSectionColor)
        )
        // Background/Bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.40f)
                .align(Alignment.BottomCenter)
                .background(
                    color = bottomSectionColor,
                    shape = RoundedCornerShape(
                        topStart = 90.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
        )
        // Foreground/Content
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .testTag("aap_title")
                    .padding(bottom = 32.dp),
                text = stringResource(id = R.string.aap_title),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                modifier = Modifier.testTag("aap_text"),
                text = stringResource(id = R.string.aap_get_started)
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .testTag("otf_access_token"),
                value = accessToken,
                singleLine = true,
                onValueChange = onAccessTokenChange,
                placeholder = { Text(text = stringResource(id = R.string.aap_access_token)) }
            )
            Button(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 80.dp)
                    .testTag("button_get_started"),
                onClick = onGetStartedButtonClick,
            ) {
                Text(text = stringResource(id = R.string.button_get_started))
            }
            when (uiState) {
                is UiState.Initial -> {}
                is UiState.Error -> {
                    errorSnackBarCallback(uiState.errorDescription)
                }
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }
                is UiState.Success -> {
                    Text(uiState.data.name)
                    Text(uiState.data.type)
                    onOpenConfirmationDialog(true)
                }
            }
        }
    }
}

@Preview
@Composable
private fun LM_AccessTokenRequestScreenPreview() {
    AllSeeTheme {
        Surface {
            AccessTokenRequestScreen(
                accessToken = "",
                onAccessTokenChange = {},
                onGetStartedButtonClick = {},
                uiState = UiState.Initial,
                errorSnackBarCallback = {},
                onOpenConfirmationDialog = {},
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun DM_AccessTokenRequestScreenPreview() {
    AllSeeTheme {
        Surface {
            AccessTokenRequestScreen(
                accessToken = "",
                onAccessTokenChange = {},
                onGetStartedButtonClick = {},
                uiState = UiState.Initial,
                errorSnackBarCallback = {},
                onOpenConfirmationDialog = {},
            )
        }
    }
}