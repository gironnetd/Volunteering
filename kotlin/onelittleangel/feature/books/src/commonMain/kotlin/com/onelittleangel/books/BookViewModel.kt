package com.onelittleangel.books

import com.onelittleangel.data.source.BookmarkRepository
import com.onelittleangel.domain.book.GetBookByNameUseCase
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.UserBook
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class BookViewModel(
    name: String,
    getBookByNameUseCase: GetBookByNameUseCase,
    private val bookmarkRepository: BookmarkRepository
): ViewModel() {

    private val _tabState = MutableStateFlow(
        BookTabState(
            titles = mutableListOf(
                Resources.strings.authors_quotes,
                Resources.strings.authors_biography,
                Resources.strings.authors_pictures),
            currentIndex = 0
        )
    )

    val tabState: StateFlow<BookTabState> = _tabState.asStateFlow()

    val bookUiState: StateFlow<BookUiState> = getBookByNameUseCase.bookUiStateStream(
        name = name,
        tabState = tabState
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = BookUiState.Loading
        )

    fun switchTab(newIndex: Int) {
        if (newIndex != tabState.value.currentIndex) {
            _tabState.update {
                it.copy(currentIndex = newIndex)
            }
        }
    }

    fun updateUserResourceBookmarked(bookmark: Bookmark, bookmarked: Boolean) {
        viewModelScope.launch {
            bookmarkRepository.updateResourceBookmarked(bookmark = bookmark, bookmarked = bookmarked)
        }
    }
}

private fun GetBookByNameUseCase.bookUiStateStream(
    name: String,
    tabState: StateFlow<BookTabState>
): Flow<BookUiState> {
    // Observe the followed authors, as they could change over time.
    return this(
        name = name
    ).map {
        if(it.biography.value == null) {
            tabState.value.titles.remove(Resources.strings.authors_quotes)
        }

        if(it.pictures == null || it.pictures!!.isEmpty()) {
            tabState.value.titles.remove(Resources.strings.authors_pictures)
        }

        BookUiState.Success(userBook = it)
    }
}

data class BookTabState(
    val titles: MutableList<StringResource>,
    val currentIndex: Int
)

sealed interface BookUiState {
    data class Success(val userBook: UserBook) : BookUiState
    object Loading : BookUiState
}