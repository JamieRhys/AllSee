package com.sycosoft.allsee.presentation.components.homepage

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sycosoft.allsee.presentation.theme.AllSeeTheme

@Composable
fun HomePageScreen(
    topSectionColor: Color = MaterialTheme.colorScheme.surface,
    bottomSectionColor: Color = MaterialTheme.colorScheme.inverseSurface,
    innerPadding: PaddingValues,
) {
    val topSectionHeight = 0.90f
    val bottomSectionHeight = 0.10f
    val roundedCornerSize = 30.dp
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .testTag("screen_home_page")
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
                .padding(top = innerPadding.calculateTopPadding())
                .constrainAs(foregroundTop) {
                    top.linkTo(backgroundTop.top)
                    bottom.linkTo(backgroundTop.bottom)
                    start.linkTo(backgroundTop.start)
                    end.linkTo(backgroundTop.end)
                }
        ) {

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

@Preview(name = "Light Mode", showSystemUi = true, showBackground = true)
@Composable
private fun LM_HomePageScreenPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            HomePageScreen(
                innerPadding = PaddingValues()
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
                innerPadding = PaddingValues()
            )
        }
    }
}