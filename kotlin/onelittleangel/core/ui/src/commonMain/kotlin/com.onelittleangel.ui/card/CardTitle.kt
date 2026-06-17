package com.onelittleangel.ui.card

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CardTitle(
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    title: String,
    modifier: Modifier = Modifier
) {
    Text(title,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier,
        color = color)
}
