package com.onelittleangel.ui.card

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.onelittleangel.designsystem.theme.PaletteTokens
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.ResourceKind
import com.onelittleangel.model.UserPicture
import com.onelittleangel.ui.button.BookmarkButton
import com.onelittleangel.ui.util.randomUUID
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
@Composable
actual fun PictureCard(
    picture: UserPicture,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
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
            modifier = modifier.fillMaxWidth().padding(all = 16.dp)) {
            Row(horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth().padding(top = 6.dp, end = 6.dp)) {
                picture.comments?.get("french")?.let {
                    Text(text = it,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f).padding(top = 10.dp, start = 8.dp, bottom = 8.dp))
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

            Image(
                modifier = Modifier.fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(
                    DrawableResource(
                        "drawable:${picture.nameSmall}_${picture.idData}",
                        setOf(
                            ResourceItem(setOf(), "drawable/${picture.nameSmall}_${picture.idData}.jpg"),
                        )
                    )
                ),
                contentDescription = null)
        }
    }
}
