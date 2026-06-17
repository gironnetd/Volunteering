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
import com.onelittleangel.model.BookmarkGroup
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.ui.Resources
import com.onelittleangel.ui.card.BookmarkGroupCard
import dev.icerock.moko.resources.compose.stringResource
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun BookmarkGroupsRoute(
    state: LazyListState,
    onBookmarkGroupClick: (BookmarkGroup) -> Unit,
    onAuthorClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: BookmarkGroupsViewModel = koinViewModel(vmClass = BookmarkGroupsViewModel::class),
) {
    val groupsUiState: BookmarkGroupsUiState by viewModel.groupsUiState.collectAsStateWithLifecycle()

    BookmarkGroupsScreen(
        state = state,
        groupsUiState = groupsUiState,
        modifier = modifier,
        onBookmarkGroupClick = onBookmarkGroupClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookmarkGroupsScreen(
    state: LazyListState,
    groupsUiState: BookmarkGroupsUiState,
    onBookmarkGroupClick: (BookmarkGroup) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.testTag("bookmarks:groups"),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp + SearchBarDefaults.InputFieldHeight.value.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp,
            end = 16.dp,
            bottom = 130.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = state
    ) {
        when(groupsUiState) {
            BookmarkGroupsUiState.Loading -> {}
            is BookmarkGroupsUiState.Success -> {
                groupCards(groupsUiState = groupsUiState, onBookmarkGroupClick = onBookmarkGroupClick)
            }
        }
    }
}

private fun LazyListScope.groupCards(
    groupsUiState: BookmarkGroupsUiState.Success,
    onBookmarkGroupClick: (BookmarkGroup) -> Unit,
) {
    groupsUiState.groups.forEach {
        item {
            it.directoryName = if(it.directoryName != "reading list") { it.directoryName }
            else { stringResource(Resources.strings.bookmarks_reading_list) }
            BookmarkGroupCard(group = it, onBookmarkGroupClick = onBookmarkGroupClick)
        }
    }
}
