package com.onelittleangel.foryou.component

//import dev.icerock.moko.resources.compose.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.model.UserPicture
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi

@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
@Composable
expect fun ForYouPictureCard(
    picture: UserPicture,
    navigateTo: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier,
)