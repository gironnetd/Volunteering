package com.onelittleangel.ui.card

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CardTextContent(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(title,
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onPrimaryContainer)
}
