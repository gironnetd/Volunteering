package com.onelittleangel.bookmarks

import com.onelittleangel.data.source.BookmarkRepository
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.BookmarkGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class BookmarkGroupsViewModel(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    val groupsUiState: StateFlow<BookmarkGroupsUiState> = bookmarkRepository.groupsUiStateStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = BookmarkGroupsUiState.Loading
        )

    fun updateUserResourceBookmarked(bookmark: Bookmark, bookmarked: Boolean) {
        viewModelScope.launch {
            bookmarkRepository.updateResourceBookmarked(bookmark = bookmark, bookmarked = bookmarked)
        }
    }

    fun createBookmarkGroup(group: BookmarkGroup) {
        viewModelScope.launch {
            bookmarkRepository.createOrUpdate(group = group)
        }
    }
}

private fun BookmarkRepository.groupsUiStateStream(): Flow<BookmarkGroupsUiState> {
    // Observe the followed authors, as they could change over time.
    return allBookmarkGroups().map {
        BookmarkGroupsUiState.Success(groups = it)
    }
}

sealed interface BookmarkGroupsUiState {
    data class Success(val groups: List<BookmarkGroup>) : BookmarkGroupsUiState
    object Loading : BookmarkGroupsUiState
}