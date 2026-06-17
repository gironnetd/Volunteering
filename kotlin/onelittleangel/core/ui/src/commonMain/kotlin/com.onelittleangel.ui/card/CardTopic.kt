package com.onelittleangel.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.onelittleangel.designsystem.theme.PaletteTokens
import com.onelittleangel.model.FollowableTopic

@Composable
fun CardTopic(
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    followableTopic: FollowableTopic,
    onTopicClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier.padding(horizontal = 6.dp)
) {
    Box(
        modifier = Modifier
            .background(
                if(PaletteTokens.LocalSystemInDarkTheme.current)
                    MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape)
            .padding(horizontal = 12.dp).padding(top = 0.dp, bottom = 0.dp).clickable { onTopicClick(followableTopic) }
    ) {
        Text(followableTopic.topic.name,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            style = textStyle,
            lineHeight = textStyle.lineHeight,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(vertical = 8.dp))
    }
}