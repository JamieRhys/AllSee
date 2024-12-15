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
import androidx.constraintlayout.compose.ConstraintLayout
import com.sycosoft.allsee.R
import com.sycosoft.allsee.presentation.theme.AllSeeTheme

@Composable
fun AccessTokenRequestScreen(
    topSectionColor: Color = MaterialTheme.colorScheme.surface,
    bottomSectionColor: Color = MaterialTheme.colorScheme.inverseSurface,
    accessToken: String,
    onAccessTokenChange: (String) -> Unit,
    showProgressBar: Boolean,
    onButtonClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .testTag(AccessTokenRequestScreenTestTags.SCREEN)
    ) {
        val (
            backgroundBottom,
            backgroundBottomLeft,
            backgroundTop,
            backgroundTopRight,
            button,
            contentTop,
            contentBottom,
        ) = createRefs()

        // Background/TopRight
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.6f)
                .background(bottomSectionColor)
                .constrainAs(backgroundTopRight) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(backgroundBottom.top)
                }
        )

        // Background/Top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .background(
                    color = topSectionColor,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 90.dp
                    ),
                )
                .constrainAs(backgroundTop) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(backgroundBottom.top)
                }
        )

        // Background/BottomLeft
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.4f)
                .background(topSectionColor)
                .constrainAs(backgroundBottomLeft) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    top.linkTo(backgroundTop.bottom)
                }
        )

        // Background/Bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(
                    color = bottomSectionColor,
                    shape = RoundedCornerShape(
                        topStart = 90.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp,
                    )
                )
                .constrainAs(backgroundBottom) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(backgroundTop.bottom)
                }
        )

        // Foreground/Button
        Button(
            modifier = Modifier
                .testTag(AccessTokenRequestScreenTestTags.BUTTON_GET_STARTED)
                .constrainAs(button) {
                    top.linkTo(backgroundTop.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(backgroundBottom.top)
                },
            onClick = { onButtonClick() },
        ) {
            Text(text = stringResource(id = R.string.button_get_started))
        }

        // Foreground/ContentTop
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(16.dp)
                .constrainAs(contentTop) {
                    bottom.linkTo(button.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .testTag(AccessTokenRequestScreenTestTags.TITLE)
                    .padding(bottom = 32.dp),
                text = stringResource(id = R.string.aap_title),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                modifier = Modifier.testTag(AccessTokenRequestScreenTestTags.TEXT),
                text = stringResource(id = R.string.aap_get_started)
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 64.dp, bottom = 16.dp)
                    .testTag(AccessTokenRequestScreenTestTags.ACCESS_TOKEN_INPUT),
                value = accessToken,
                singleLine = true,
                onValueChange = onAccessTokenChange,
                placeholder = { Text(text = stringResource(id = R.string.aap_access_token)) }
            )
        }

        // Foreground/ContentBottom
        Column(
            modifier = Modifier
                .constrainAs(contentBottom) {
                    bottom.linkTo(parent.bottom)
                    top.linkTo(button.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            when {
                showProgressBar -> CircularProgressIndicator(
                    modifier = Modifier.testTag(AccessTokenRequestScreenTestTags.PROGRESS_BAR)
                )
            }
        }
    }
}

@Preview
@Composable
private fun LM_AccessTokenRequestScreenPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            AccessTokenRequestScreen(
                accessToken = "",
                onAccessTokenChange = {},
                onButtonClick = {},
                showProgressBar = false,
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun DM_AccessTokenRequestScreenPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            AccessTokenRequestScreen(
                accessToken = "",
                onAccessTokenChange = {},
                onButtonClick = {},
                showProgressBar = false,
            )
        }
    }
}