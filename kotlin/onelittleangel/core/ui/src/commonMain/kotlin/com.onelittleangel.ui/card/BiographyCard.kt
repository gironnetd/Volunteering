package com.onelittleangel.ui.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.onelittleangel.designsystem.theme.PaletteTokens
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.ResourceKind
import com.onelittleangel.model.UserBiography
import com.onelittleangel.ui.button.BookmarkButton
import com.onelittleangel.ui.util.randomUUID
import kotlinx.datetime.Clock

@Composable
fun BiographyCard(
    biography: UserBiography,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onAuthorClick: (String) -> Unit,
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
            .shadow(
                shape = RoundedCornerShape(8.dp),
                elevation = 2.dp,
                ambientColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))

    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {

            biography.presentation?.let {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()) {
                    CardTitle(title = "Introduction", modifier = Modifier.padding(top = 10.dp))

                    val isBookmarked = remember { derivedStateOf { biography.isSaved } }

                    BookmarkButton(
                        isBookmarked = isBookmarked.value,
                        onClick = {
                            onToggleBookmark(
                                Bookmark(
                                    id = randomUUID(),
                                    idBookmarkGroup = "",
                                    note = "",
                                    idResource = biography.idPresentation,
                                    kind = ResourceKind.biography,
                                    dateCreation = Clock.System.now()
                                ), it
                            )
                        },
                        modifier = Modifier.offset(x = 12.dp)
                    )
                }

                CardTextContent(it)
            }

            biography.presentationTitle1?.let { presentationTitle1 ->
                CardTitle(title = presentationTitle1)

                biography.presentation1?.let { presentation1 ->
                    CardTextContent(presentation1)
                }
            }

            biography.presentationTitle2?.let { presentationTitle2 ->
                CardTitle(title = presentationTitle2)

                biography.presentation2?.let { presentation2 ->
                    CardTextContent(presentation2)
                }
            }

            biography.presentationTitle3?.let { presentationTitle3 ->
                CardTitle(title = presentationTitle3)

                biography.presentation3?.let { presentation3 ->
                    CardTextContent(presentation3)
                }
            }

            biography.presentationTitle4?.let { presentationTitle4 ->
                CardTitle(title = presentationTitle4)

                biography.presentation4?.let { presentation4 ->
                    CardTextContent(presentation4)
                }
            }
        }
    }
}
