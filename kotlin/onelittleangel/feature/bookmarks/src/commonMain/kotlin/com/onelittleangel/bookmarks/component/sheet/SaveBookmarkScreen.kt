package com.onelittleangel.bookmarks.component.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.onelittleangel.bookmarks.BookmarkGroupsUiState
import com.onelittleangel.bookmarks.BookmarkGroupsViewModel
import com.onelittleangel.bookmarks.Resources
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.BookmarkGroup
import com.onelittleangel.ui.util.randomUUID
import dev.icerock.moko.resources.compose.stringResource
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun SaveBookmarkRoute(
    modifier: Modifier = Modifier,
    bookmark: Bookmark,
    showCreateBookmarkGroup: () -> Unit,
    viewModel: BookmarkGroupsViewModel = koinViewModel(vmClass = BookmarkGroupsViewModel::class),
) {
    val groupsUiState: BookmarkGroupsUiState by viewModel.groupsUiState.collectAsStateWithLifecycle()

    SaveBookmarkScreen(
        groupsUiState = groupsUiState,
        modifier = modifier,
        showCreateBookmarkGroup = showCreateBookmarkGroup,
        saveBookmark = viewModel::updateUserResourceBookmarked,
        bookmark = bookmark
    )
}

@Composable
internal fun SaveBookmarkScreen(
    bookmark: Bookmark,
    groupsUiState: BookmarkGroupsUiState,
    saveBookmark: (Bookmark, Boolean) -> Unit,
    showCreateBookmarkGroup: () -> Unit,
    //onBookmarkGroupClick: (BookmarkGroup) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        //verticalArrangement = Arrangement.spacedBy(12.dp),
        //state = state
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                Text(
                    stringResource(Resources.strings.bookmarks_save_to),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    stringResource(Resources.strings.bookmarks_create_bookmarks),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { showCreateBookmarkGroup() }
                )
            }

        }

        when(groupsUiState) {
            BookmarkGroupsUiState.Loading -> {}
            is BookmarkGroupsUiState.Success -> {
                groupCards(
                    groupsUiState = groupsUiState,
                    onBookmarkGroupClick = {},
                    bookmark = bookmark,
                    saveBookmark = saveBookmark)
            }
        }
    }
}

private fun LazyListScope.groupCards(
    bookmark: Bookmark,
    saveBookmark: (Bookmark, Boolean) -> Unit,
    groupsUiState: BookmarkGroupsUiState.Success,
    onBookmarkGroupClick: (BookmarkGroup) -> Unit,
) {
    groupsUiState.groups.forEach { group ->
        item {
            var checked by remember { mutableStateOf(group.bookmarks.any { it.idResource == bookmark.idResource }) }

            Row(modifier = Modifier.fillMaxWidth().clickable {
                checked = !checked
                bookmark.idBookmarkGroup = group.id
                saveBookmark(bookmark.apply { this.id = randomUUID() }, checked)
            },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {

                Checkbox(
                    onCheckedChange = {
                        checked = !checked
                        bookmark.idBookmarkGroup = group.id
                        saveBookmark(bookmark.apply { this.id = randomUUID() }, checked)
                    },
                    checked = checked,
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )

                Text(
                    if(group.directoryName != "reading list") { group.directoryName }
                    else { stringResource(Resources.strings.bookmarks_reading_list) },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
