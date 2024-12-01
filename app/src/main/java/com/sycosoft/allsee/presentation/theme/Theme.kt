package com.sycosoft.allsee.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ForestGreen,
    secondary = NavyBlue,
    tertiary = DarkBrown,
    onPrimary = Color.White,
    onSecondary = Color.White,
    surface = NavyBlue,
    inverseSurface = OffWhite,
)

private val LightColorScheme = lightColorScheme(
    primary = ForestGreen,
    secondary = NavyBlue,
    tertiary = DarkBrown,
    onPrimary = Color.White,
    onSecondary = Color.White,
    surface = OffWhite,
    inverseSurface = NavyBlue,
)

@Composable
fun AllSeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}