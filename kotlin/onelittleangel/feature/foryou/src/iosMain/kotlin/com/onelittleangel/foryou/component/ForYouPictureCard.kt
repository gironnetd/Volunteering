package com.onelittleangel.foryou.component

//import dev.icerock.moko.resources.compose.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.onelittleangel.cache.Resources
import com.onelittleangel.designsystem.theme.PaletteTokens
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.model.ResourceKind
import com.onelittleangel.model.UserPicture
import com.onelittleangel.ui.button.BookmarkButton
import com.onelittleangel.ui.card.CardShortDescription
import com.onelittleangel.ui.card.CardTitle
import com.onelittleangel.ui.card.CardTopic
import com.onelittleangel.ui.util.randomUUID
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.getImageByFileName
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi

@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
@Composable
actual fun ForYouPictureCard(
    picture: UserPicture,
    navigateTo: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    modifier: Modifier,
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
            .shadow(
                shape = RoundedCornerShape(8.dp),
                elevation = 2.dp,
                ambientColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).padding(horizontal = 16.dp)
                .padding(bottom = 16.dp).fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            picture.followableTopics?.first()?.let { followableTopic ->
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top) {

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f).padding(top = 10.dp, start = 8.dp, end = 8.dp)) {
                        CardTitle(title = followableTopic.topic.name)

                        followableTopic.topic.shortDescription?.let { shortDescription ->
                            CardShortDescription(shortDescription = shortDescription)
                        }
                    }

                    val isBookmarked = remember { derivedStateOf { picture.isSaved } }

                    BookmarkButton(
                        isBookmarked = isBookmarked.value,
                        onClick = {
                            onToggleBookmark(
                                Bookmark(
                                    id = randomUUID(),
                                    idBookmarkGroup = "",
                                    note = "",
                                    idResource = picture.idPicture,
                                    kind = ResourceKind.picture,
                                    dateCreation = Clock.System.now()
                                ), it
                            )
                        },
                        modifier = Modifier.offset(x = 12.dp)
                    )
                }
            }

            Resources.images.getImageByFileName("${picture.nameSmall}_${picture.idData}")?.let {
                Image(
                    modifier = Modifier.fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(it),
                    contentDescription = null)
            } ?:
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp)
                    .aspectRatio(picture.width.toFloat() / picture.height.toFloat())
                    .clip(RoundedCornerShape(8.dp)).background(Color.Gray.copy(alpha = 0.1f))
            )



            /*picture.picture?.let {

            } ?:
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp)
                    .aspectRatio(picture.width.toFloat() / picture.height.toFloat())
                    .clip(RoundedCornerShape(8.dp)).background(Color.Gray.copy(alpha = 0.1f))
            )*/

            Column(verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                picture.followableTopics?.let { it ->
                    it.filter { it.topic.language.subSequence(0, 2) == Locale.current.language }
                        .filter { it.topic.kind != ResourceKind.century }.forEach {
                            CardTopic(
                                followableTopic =  it,
                                onTopicClick = navigateTo)
                        }
                }
            }

            /*LazyHorizontalStaggeredGrid(
                modifier = Modifier.fillMaxWidth().height(40.dp)*//*.padding(top = 8.dp)*//*,
                rows = StaggeredGridCells.Adaptive(30.dp),
                //reverseLayout = true,
                //contentPadding = PaddingValues(16.dp),
                horizontalItemSpacing = 8.dp,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                picture.followableTopics?.let { it ->
                    items(it.filter { it.topic.kind != ResourceKind.century }) {
                        CardTopic(it.topic.name, onTopicClick =  onTopicClick)
                    }
                }
            }*/
        }
    }
}