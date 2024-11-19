package com.sycosoft.allsee.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun Header1(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 64.sp,
        fontFamily = FontFamily.Cursive,
        color = color,
    )
}

@Composable
fun Normal(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign? = null,
    color: Color = MaterialTheme.colorScheme.onBackground,
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = FontFamily.SansSerif,
        textAlign = textAlign,
        color = color,
    )
}