package com.onelittleangel.themes

import com.onelittleangel.data.source.BookmarkRepository
import com.onelittleangel.domain.theme.GetThemeByNameUseCase
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.UserTheme
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

class ThemeViewModel(
    name: String,
    getThemeByNameUseCase: GetThemeByNameUseCase,
    private val bookmarkRepository: BookmarkRepository
): ViewModel() {

    private val _tabState = MutableStateFlow(
        ThemeTabState(
            titles = mutableListOf(
                Resources.strings.authors_quotes,
                Resources.strings.authors_pictures),
            currentIndex = 0
        )
    )

    val tabState: StateFlow<ThemeTabState> = _tabState.asStateFlow()

    val themeUiState: StateFlow<ThemeUiState> = getThemeByNameUseCase.themeUiStateStream(
        name = name,
        tabState = tabState
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ThemeUiState.Loading
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

private fun GetThemeByNameUseCase.themeUiStateStream(
    name: String,
    tabState: StateFlow<ThemeTabState>
): Flow<ThemeUiState> {
    // Observe the followed authors, as they could change over time.
    return this(
        name = name
    ).map {
        if(it.pictures == null || it.pictures!!.isEmpty()) {
            tabState.value.titles.remove(Resources.strings.authors_pictures)
        }

        ThemeUiState.Success(userTheme = it)
    }
}

data class ThemeTabState(
    val titles: MutableList<StringResource>,
    val currentIndex: Int
)

sealed interface ThemeUiState {
    data class Success(val userTheme: UserTheme) : ThemeUiState
    object Loading : ThemeUiState
}