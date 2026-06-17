package com.onelittleangel.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.onelittleangel.bookmarks.component.BookmarkedBiographyCard
import com.onelittleangel.bookmarks.component.BookmarkedPictureCard
import com.onelittleangel.bookmarks.component.BookmarkedQuoteCard
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.model.UserBiography
import com.onelittleangel.model.UserPicture
import com.onelittleangel.model.UserQuote
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BookmarksRoute(
    bookmarkGroupId: String,
    state: LazyListState,
    navigateTo: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onBackClick: () -> Unit,
    onAuthorClick: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: BookmarksViewModel = koinViewModel(vmClass = BookmarksViewModel::class)  { parametersOf(bookmarkGroupId) },
) {
    val bookmarksUiState: BookmarksUiState by viewModel.bookmarksUiState.collectAsStateWithLifecycle()

    BookmarksScreen(
        state = state,
        bookmarksUiState = bookmarksUiState,
        modifier = modifier,
        onAuthorClick = onAuthorClick,
        navigateTo = navigateTo,
        onToggleBookmark = onToggleBookmark,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookmarksScreen(
    navigateTo: (FollowableTopic) -> Unit,
    state: LazyListState,
    bookmarksUiState: BookmarksUiState,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onAuthorClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.testTag("bookmarks:bookmarks"),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp + SearchBarDefaults.InputFieldHeight.value.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp,
            end = 16.dp,
            bottom = 130.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = state
    ) {
        when(bookmarksUiState) {
            BookmarksUiState.Loading -> {}
            is BookmarksUiState.Success -> {
                bookmarkCards(
                    bookmarksUiState = bookmarksUiState,
                    onAuthorClick = onAuthorClick,
                    onToggleBookmark = onToggleBookmark,
                    navigateTo = navigateTo)
            }
        }
    }
}

private fun LazyListScope.bookmarkCards(
    navigateTo: (FollowableTopic) -> Unit,
    bookmarksUiState: BookmarksUiState.Success,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onAuthorClick: (String) -> Unit,
) {
    bookmarksUiState.resources.forEach { resource ->
        item {
            when(resource) {
                is UserQuote ->
                    BookmarkedQuoteCard(
                        quote = resource,
                        onToggleBookmark = onToggleBookmark/*{ _, _ ->
                            showBottomSheet.value = !showBottomSheet.value
                        }*/,
                        onTopicClick = {},
                        navigateTo = navigateTo
                    )
                is UserPicture ->
                    BookmarkedPictureCard(
                        picture = resource,
                        onToggleBookmark = onToggleBookmark/*{ _, _ ->
                            showBottomSheet.value = !showBottomSheet.value
                        }*/,
                        onTopicClick = {},
                        navigateTo = navigateTo,
                    )
                is UserBiography ->
                    BookmarkedBiographyCard(
                        biography = resource,
                        onToggleBookmark = onToggleBookmark/*{ _, _ ->
                            showBottomSheet.value = !showBottomSheet.value
                        }*/,
                        onTopicClick = {},
                        navigateTo = navigateTo,
                    )
            }
        }
    }
}
