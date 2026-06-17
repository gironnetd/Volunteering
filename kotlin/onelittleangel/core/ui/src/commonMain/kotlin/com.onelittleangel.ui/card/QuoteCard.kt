package com.onelittleangel.ui.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.onelittleangel.designsystem.theme.PaletteTokens
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.model.ResourceKind
import com.onelittleangel.model.UserQuote
import com.onelittleangel.ui.button.BookmarkButton
import com.onelittleangel.ui.util.randomUUID
import kotlinx.datetime.Clock

@Composable
fun QuoteCard(
    quote: UserQuote,
    navigateTo: (FollowableTopic) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
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
            .testTag(quote.idQuote)
            .shadow(
                shape = RoundedCornerShape(8.dp),
                elevation = 2.dp,
                ambientColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(top = 10.dp).padding(horizontal = 16.dp).padding(bottom = 16.dp).fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()) {
                quote.source?.let {
                    Text(text = it,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f).padding(top = 10.dp, end = 8.dp))
                } ?: Text("")

                val isBookmarked = remember { derivedStateOf { quote.isSaved } }

                BookmarkButton(
                    isBookmarked = isBookmarked.value,
                    onClick = {
                        onToggleBookmark(
                            Bookmark(
                                id = randomUUID(),
                                idBookmarkGroup = "",
                                note = "",
                                idResource = quote.idQuote,
                                kind = ResourceKind.quote,
                                dateCreation = Clock.System.now()
                            ), it
                        )
                    },
                    modifier = Modifier.offset(x = 12.dp)
                )
            }

            Text(text = quote.quote,
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyLarge)

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                quote.followableTopics?.let { it ->
                    it.filter { it.topic.kind != ResourceKind.century }.forEach {
                        CardTopic(followableTopic =  it, onTopicClick = navigateTo)
                    }
                }
            }
        }
    }
}
