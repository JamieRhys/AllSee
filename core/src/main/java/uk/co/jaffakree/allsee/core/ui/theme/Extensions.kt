package uk.co.jaffakree.allsee.core.ui.theme

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.max

@Composable
fun Modifier.shimmerBackground(colors: List<Color>): Modifier {
    val transition = rememberInfiniteTransition()
    val start = 0f
    val gradientWidth = 400
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 1600), repeatMode = RepeatMode.Restart)
    )
    val startXYOffset = max(a = start, b = translateAnimation - gradientWidth)
    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(x = startXYOffset, y = startXYOffset),
        end = Offset(x = translateAnimation, y = translateAnimation)
    )
    return this then Modifier.background(brush = brush)
}