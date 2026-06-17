package com.onelittleangel.ui.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.UserPicture

@Composable
expect fun PictureCard(
    picture: UserPicture,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    modifier: Modifier = Modifier,
)