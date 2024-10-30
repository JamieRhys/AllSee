package com.sycosoft.allsee.ui.components

import android.content.res.Configuration
import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sycosoft.allsee.R
import com.sycosoft.allsee.ui.theme.AllSeeTheme
import com.sycosoft.allsee.ui.theme.OffWhite

@Composable
fun InternetLostScreen(
    outsideSectionColor: Color = MaterialTheme.colorScheme.background,
    insideSectionColor: Color = OffWhite,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background/TopLeft
        Box(
            modifier = Modifier
                .fillMaxWidth(0.33f)
                .fillMaxHeight(0.30f)
                .align(Alignment.TopStart)
                .background(color = outsideSectionColor)
        )

        // Background/TopRight
        Box(
            modifier = Modifier
                .fillMaxWidth(0.33f)
                .fillMaxHeight(0.30f)
                .align(Alignment.TopEnd)
                .background(color = insideSectionColor)
        )

        // Background/Top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .align(Alignment.TopCenter)
                .background(
                    color = outsideSectionColor,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 90.dp,
                    )
                )
        )

        // Background/BottomLeft
        Box(
            modifier = Modifier
                .fillMaxWidth(0.33f)
                .fillMaxHeight(0.30f)
                .align(Alignment.BottomEnd)
                .background(color = outsideSectionColor)
        )

        // Background/Middle
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .align(Alignment.Center)
                .background(
                    color = insideSectionColor,
                    shape = RoundedCornerShape(
                        topStart = 90.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 90.dp,
                    )
                )
        )

        // Background/BottomRight
        Box(
            modifier = Modifier
                .fillMaxWidth(0.33f)
                .fillMaxHeight(0.30f)
                .align(Alignment.BottomStart)
                .background(color = insideSectionColor)
        )

        // Background/Bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .align(Alignment.BottomCenter)
                .background(
                    color = outsideSectionColor,
                    shape = RoundedCornerShape(
                        topStart = 90.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp,
                    )
                )
        )

        // Foreground/Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                modifier = Modifier.size(128.dp),
                imageVector = Icons.Default.WifiOff,
                contentDescription = "Wifi not found",
                tint = Color.Red
            )
            Normal(
                text = stringResource(id = R.string.error_internet_lost),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun LM_InternetLostScreenPreview() {
    AllSeeTheme {
        Surface {
            InternetLostScreen()
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun DM_InternetLostScreenPreview() {
    AllSeeTheme {
        Surface {
            InternetLostScreen()
        }
    }
}