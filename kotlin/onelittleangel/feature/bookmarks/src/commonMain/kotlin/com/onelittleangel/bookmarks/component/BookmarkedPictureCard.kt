package com.onelittleangel.bookmarks.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.model.UserPicture

@Composable
expect fun BookmarkedPictureCard(
    picture: UserPicture,
    navigateTo: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier,
)