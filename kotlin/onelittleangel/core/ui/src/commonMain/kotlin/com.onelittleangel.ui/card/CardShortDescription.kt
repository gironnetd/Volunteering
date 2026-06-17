package com.onelittleangel.ui.card

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CardShortDescription(
    shortDescription: String,
    modifier: Modifier = Modifier
) {
    Text(shortDescription,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onPrimary
    )
}