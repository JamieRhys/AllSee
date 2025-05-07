package uk.co.jaffakree.allsee.core.presentation.theme

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
    val transitionAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart,
        )
    )
    val startXYOffset = max(a = start, b = transitionAnimation - gradientWidth)
    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(x = startXYOffset, y = startXYOffset),
        end = Offset(x = transitionAnimation, y = transitionAnimation)
    )

    return this then Modifier.background(brush = brush)
}

/** The main background for each screen we use within the app. This is a linear gradient from the
 * top left corner, to bottom right.
 *
 * @property backgroundColor1 The first Colour in the gradient. This is normally the prominent colour
 * @property backgroundColor2 The second Color in the gradient. This is normally the background colour
 */
@Composable
fun Modifier.mainBackground(
    backgroundColor1: Color = MaterialTheme.colorScheme.surface,
    backgroundColor2: Color = MaterialTheme.colorScheme.background,
): Modifier = this then Modifier
    .fillMaxSize()
    .background(
        brush = Brush.linearGradient(
            listOf(
                backgroundColor1,
                backgroundColor2,
            ),
            start = Offset.Zero,
            end = Offset.Infinite,
        )
    )