package com.sycosoft.allsee.presentation.components.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.sycosoft.allsee.presentation.theme.shimmerBackground

sealed interface DynamicTextType {
    data object Placeholder : DynamicTextType
    data class Value(val text: String) : DynamicTextType
}

@Composable
fun DynamicText(
    modifier: Modifier,
    style: TextStyle = LocalTextStyle.current,
    value: DynamicTextType,
) {
    when (value) {
        is DynamicTextType.Placeholder -> {
            Box(
                modifier = modifier
                    .clip(CircleShape)
                    .height(18.dp)
                    .shimmerBackground(
                        listOf(
                            LocalContentColor.current.copy(alpha = 0.1f),
                            LocalContentColor.current.copy(alpha = 0.05f),
                            LocalContentColor.current.copy(alpha = 0.1f),
                        )
                    )
            )
        }
        is DynamicTextType.Value -> {
            Text(
                modifier = modifier,
                text = value.text,
                style = style,
            )
        }
    }
}