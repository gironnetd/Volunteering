package com.onelittleangel.authors

import com.onelittleangel.data.source.BookmarkRepository
import com.onelittleangel.domain.author.GetAuthorByNameUseCase
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.UserAuthor
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

class AuthorViewModel(
    name: String,
    getAuthorByNameUseCase: GetAuthorByNameUseCase,
    private val bookmarkRepository: BookmarkRepository
): ViewModel() {

    private val _tabState = MutableStateFlow(
        AuthorTabState(
            titles = mutableListOf(
                Resources.strings.authors_quotes,
                Resources.strings.authors_biography,
                Resources.strings.authors_pictures),
            currentIndex = 0
        )
    )

    val tabState: StateFlow<AuthorTabState> = _tabState.asStateFlow()

    val authorUiState: StateFlow<AuthorUiState> = getAuthorByNameUseCase.authorUiStateStream(
        name = name,
        tabState = tabState
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = AuthorUiState.Loading
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

private fun GetAuthorByNameUseCase.authorUiStateStream(
    name: String,
    tabState: StateFlow<AuthorTabState>
): Flow<AuthorUiState> {
    // Observe the followed authors, as they could change over time.
    return this(
        name = name
    ).map {
        if(it.biography.value == null) {
            tabState.value.titles.remove(Resources.strings.authors_biography)
        }

        if(it.pictures == null || it.pictures!!.isEmpty()) {
            tabState.value.titles.remove(Resources.strings.authors_pictures)
        }

        AuthorUiState.Success(userAuthor = it)
    }
}

data class AuthorTabState(
    val titles: MutableList<StringResource>,
    val currentIndex: Int
)

sealed interface AuthorUiState {
    data class Success(val userAuthor: UserAuthor) : AuthorUiState
    object Loading : AuthorUiState
}