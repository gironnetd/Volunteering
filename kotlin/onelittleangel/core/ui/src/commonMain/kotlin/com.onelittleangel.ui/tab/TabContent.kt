package com.onelittleangel.ui.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.model.UserBiography
import com.onelittleangel.model.UserPicture
import com.onelittleangel.model.UserQuote
import com.onelittleangel.ui.card.BiographyCard
import com.onelittleangel.ui.card.PictureCard
import com.onelittleangel.ui.card.QuoteCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesTabContent(
    state: LazyListState,
    quotes: SnapshotStateList<UserQuote>,
    navigateTo: (FollowableTopic) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = state,
        modifier = modifier.testTag("quotes:tabContent"),
        contentPadding = PaddingValues(
            top = (SearchBarDefaults.InputFieldHeight.value.dp * 2 +
                    WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp + 16.dp),
            start = 16.dp,
            end = 16.dp,
            bottom = 130.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            quotes,
            key =  { it.hashCode() + it.isSaved.hashCode() }
        ) {quote ->
            QuoteCard(quote = quote,
                onTopicClick = onTopicClick,
                navigateTo = navigateTo,
                onToggleBookmark = onToggleBookmark)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiographyTabContent(
    state: LazyListState,
    biography: MutableState<UserBiography>,
    navigateTo: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = state,
        modifier = modifier.fillMaxSize().testTag("biography:tabContent"),
        contentPadding = PaddingValues(
            top = (SearchBarDefaults.InputFieldHeight.value.dp * 2 +
                    WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp + 16.dp),
            start = 16.dp,
            end = 16.dp,
            bottom = 130.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(
            key = biography.value.isSaved.hashCode()
        ) {
            BiographyCard(
                biography = biography.value,
                onAuthorClick = {},
                modifier = modifier,
                onToggleBookmark = onToggleBookmark
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PicturesTabContent(
    state: LazyListState,
    pictures: SnapshotStateList<UserPicture>,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxSize().testTag("pictures::tabContent"),
        contentPadding = PaddingValues(
            top = (SearchBarDefaults.InputFieldHeight.value.dp * 2 +
                    WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp + 16.dp),
            start = 16.dp,
            end = 16.dp,
            bottom = 130.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            pictures,
            key = { it.hashCode() + it.isSaved.hashCode() }
        ) {picture ->
            PictureCard(picture = picture, onToggleBookmark = onToggleBookmark)
        }
    }
}
