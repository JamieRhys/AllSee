package com.sycosoft.allsee.presentation.components.screens.homepage

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sycosoft.allsee.presentation.components.cards.balancecard.BalanceCard
import com.sycosoft.allsee.presentation.components.cards.balancecard.BalanceCardType
import com.sycosoft.allsee.presentation.components.text.DynamicText
import com.sycosoft.allsee.presentation.components.text.DynamicTextType
import com.sycosoft.allsee.presentation.theme.AllSeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageScreen(
    topSectionColor: Color = MaterialTheme.colorScheme.surface,
    bottomSectionColor: Color = MaterialTheme.colorScheme.inverseSurface,
    accountName: DynamicTextType,
    clearedBalance: BalanceCardType,
    onPersonButtonClick: () -> Unit,
    onBalanceCardClick: () -> Unit,
) {
    val topSectionHeight = 0.90f
    val bottomSectionHeight = 0.10f
    val roundedCornerSize = 30.dp

    Scaffold(
        modifier = Modifier.testTag(HomePageScreenTestTags.SCREEN),
        topBar = {
            TopAppBar(
                title = {
                    DynamicText(
                        modifier = Modifier
                            .testTag(HomePageScreenTestTags.TEXT_ACCOUNT_NAME)
                            .padding(start = 5.dp)
                            .fillMaxWidth(fraction = 0.35f),
                        value = accountName,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                actions = {
                    IconButton(
                        modifier = Modifier.testTag(HomePageScreenTestTags.BUTTON_PERSON),
                        onClick = onPersonButtonClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            val (
                backgroundTop,
                backgroundTopRight,
                backgroundBottomLeft,
                backgroundBottom,
                foregroundTop,
                foregroundBottom,
            ) = createRefs()

            // Background/TopRight
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .fillMaxHeight(topSectionHeight)
                    .background(color = bottomSectionColor)
                    .constrainAs(backgroundTopRight) {
                        top.linkTo(parent.top)
                        bottom.linkTo(backgroundBottom.top)
                        end.linkTo(parent.end)
                    }
            )

            // Background/Top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(topSectionHeight)
                    .background(
                        color = topSectionColor,
                        shape = RoundedCornerShape(bottomEnd = roundedCornerSize)
                    )
                    .constrainAs(backgroundTop) {
                        top.linkTo(parent.top)
                        bottom.linkTo(backgroundBottom.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            // Background/BottomLeft
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .fillMaxHeight(bottomSectionHeight)
                    .background(color = topSectionColor)
                    .constrainAs(backgroundBottomLeft) {
                        top.linkTo(backgroundTop.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            )

            // Background/Bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(bottomSectionHeight)
                    .background(
                        color = bottomSectionColor,
                        shape = RoundedCornerShape(topStart = roundedCornerSize)
                    )
                    .constrainAs(backgroundBottom) {
                        top.linkTo(backgroundTop.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            // Foreground/Top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(topSectionHeight)
                    .constrainAs(foregroundTop) {
                        top.linkTo(backgroundTop.top)
                        bottom.linkTo(backgroundTop.bottom)
                        start.linkTo(backgroundTop.start)
                        end.linkTo(backgroundTop.end)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    BalanceCard(
                        value = clearedBalance,
                        onClick = { onBalanceCardClick() }
                    )
                }
            }

            // Foreground/Bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(bottomSectionHeight)
                    .constrainAs(foregroundBottom) {
                        top.linkTo(backgroundBottom.top)
                        bottom.linkTo(backgroundBottom.bottom)
                        start.linkTo(backgroundBottom.start)
                        end.linkTo(backgroundBottom.end)
                    }
            ) {

            }
        }
    }
}

@Preview(name = "Light Mode", showSystemUi = true, showBackground = true)
@Composable
private fun LM_HomePageScreenPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            HomePageScreen(
                accountName = DynamicTextType.Value("Individual"),
                clearedBalance = BalanceCardType.Value("£1,000"),
                onPersonButtonClick = {},
                onBalanceCardClick = {},
            )
        }
    }
}

@Preview(name = "Light Mode (Placeholder)", showSystemUi = true, showBackground = true)
@Composable
private fun LM_HomePageScreenPlaceholderPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            HomePageScreen(
                accountName = DynamicTextType.Placeholder,
                clearedBalance = BalanceCardType.Placeholder,
                onPersonButtonClick = {},
                onBalanceCardClick = {},
            )
        }
    }
}

@Preview(name = "Dark Mode", showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun DM_HomePageScreenPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            HomePageScreen(
                accountName = DynamicTextType.Value("Individual"),
                clearedBalance = BalanceCardType.Value("£1,000"),
                onPersonButtonClick = {},
                onBalanceCardClick = {},
            )
        }
    }
}

@Preview(name = "Dark Mode (Placeholder)", showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun DM_HomePageScreenPlaceholderPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            HomePageScreen(
                accountName = DynamicTextType.Placeholder,
                clearedBalance = BalanceCardType.Placeholder,
                onPersonButtonClick = {},
                onBalanceCardClick = {},
            )
        }
    }
}