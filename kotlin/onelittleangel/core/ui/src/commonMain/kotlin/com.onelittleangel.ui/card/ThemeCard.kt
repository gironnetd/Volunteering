package com.onelittleangel.ui.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.onelittleangel.designsystem.theme.PaletteTokens
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.model.UserTheme

@Composable
fun ThemeCard(
    theme: UserTheme,
    onThemeClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor =  if(PaletteTokens.LocalSystemInDarkTheme.current)
                MaterialTheme.colorScheme.onPrimary
            else
                PaletteTokens.White
        ),
        modifier = modifier
            .testTag(theme.idTheme)
            .shadow(
                shape = RoundedCornerShape(8.dp),
                elevation = 2.dp,
                ambientColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
            .clickable { theme.followableTopics?.first()?.let { onThemeClick(it) } }
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CardTitle(title = theme.name)

            theme.followableTopics?.first()?.topic?.shortDescription?.let {
                CardShortDescription(shortDescription = it)
            }
        }
    }
}

