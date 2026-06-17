package com.onelittleangel.foryou.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.onelittleangel.designsystem.theme.PaletteTokens
import com.onelittleangel.designsystem.theme.PaletteTokens.LocalSystemInDarkTheme
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.model.ResourceKind
import com.onelittleangel.model.UserQuote
import com.onelittleangel.ui.button.BookmarkButton
import com.onelittleangel.ui.card.CardShortDescription
import com.onelittleangel.ui.card.CardTitle
import com.onelittleangel.ui.util.randomUUID
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
@Composable
fun ForYouQuoteCard(
    quote: UserQuote,
    navigateTo: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if(LocalSystemInDarkTheme.current)
                MaterialTheme.colorScheme.onPrimary
            else
                PaletteTokens.White
        ),
        modifier = modifier
            .shadow(
                shape = RoundedCornerShape(8.dp),
                elevation = 2.dp,
                ambientColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            )
            .background(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                quote.followableTopics?.first()?.let {
                    navigateTo(it)
                }
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()//.padding(top = 10.dp).padding(horizontal = 16.dp)
                .padding(bottom = 16.dp).fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            quote.followableTopics?.first()?.let { followableTopic ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.weight(1f)//.padding(top = 10.dp, end = 8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().height(250.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Box(Modifier.fillMaxSize()) {
                                Image(
                                    modifier = Modifier.fillMaxSize()
                                        //.padding(horizontal = 16.dp)
                                        //.padding(start = 8.dp, end = 8.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.FillWidth,
                                    painter = painterResource(
                                        DrawableResource(
                                            "drawable:Nagarjuna_227",
                                            setOf(
                                                ResourceItem(setOf(), "drawable/Nagarjuna_227.jpg"),
                                            )
                                        )
                                    ),
                                    contentDescription = null)

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
                                    modifier = Modifier.align(Alignment.TopEnd)
                                )

                                Box(Modifier.align(Alignment.TopStart).padding(top = 16.dp, start = 16.dp)) {
                                    CardTitle(title = followableTopic.topic.name, color = PaletteTokens.White)
                                }

                                followableTopic.topic.shortDescription?.let { shortDescription ->
                                    Box(Modifier.padding(horizontal = 16.dp).align(Alignment.BottomStart)) {
                                        CardShortDescription(
                                            shortDescription = shortDescription,
                                            modifier = Modifier.padding(bottom = 16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Text(
                text = quote.quote,
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
            )

            /*quote.followableTopics?.first()?.let {
                Box(Modifier.padding(horizontal = 16.dp)) {
                    CardTopic(
                        followableTopic = it,
                        onTopicClick = navigateTo,
                        textStyle = MaterialTheme.typography.headlineSmall
                    )
                }
            }*/

            /*Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                quote.followableTopics?.let { it ->
                    it.filter {
                        it.topic.kind != ResourceKind.century &&
                        it.topic.kind != ResourceKind.author &&
                        it.topic.kind != ResourceKind.book
                    }.forEach {
                        CardTopic( followableTopic =  it, onTopicClick = navigateTo)
                    }
                }
            }*/
        }
    }
}
