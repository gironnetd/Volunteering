package com.onelittleangel.bookmarks

import com.onelittleangel.data.source.BookmarkRepository
import com.onelittleangel.domain.bookmark.GetBookmarkGroupByIdUseCase
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.UserResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class BookmarksViewModel(
    groupId: String,
    getBookmarkGroupByIdUseCase: GetBookmarkGroupByIdUseCase,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    val bookmarksUiState: StateFlow<BookmarksUiState> = getBookmarkGroupByIdUseCase
        .bookmarksUiStateStream(groupId = groupId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = BookmarksUiState.Loading
        )

    fun updateUserResourceBookmarked(bookmark: Bookmark, bookmarked: Boolean) {
        viewModelScope.launch {
            bookmarkRepository.updateResourceBookmarked(bookmark = bookmark, bookmarked = bookmarked)
        }
    }
}

private fun GetBookmarkGroupByIdUseCase.bookmarksUiStateStream(
    groupId: String
): Flow<BookmarksUiState> {
    // Observe the followed authors, as they could change over time.
    return this(groupId).map { resources ->
        BookmarksUiState.Success(resources = resources)
    }
}

sealed interface BookmarksUiState {
    data class Success(val resources: List<UserResource>) : BookmarksUiState
    object Loading : BookmarksUiState
}
