package com.onelittleangel.faiths

import com.onelittleangel.data.source.BookmarkRepository
import com.onelittleangel.domain.faith.GetFaithByNameUseCase
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.UserFaith
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

class FaithViewModel(
    name: String,
    getFaithByNameUseCase: GetFaithByNameUseCase,
    private val bookmarkRepository: BookmarkRepository
): ViewModel() {

    private val _tabState = MutableStateFlow(
        FaithTabState(
            titles = mutableListOf(
                Resources.strings.authors_quotes,
                Resources.strings.authors_biography,
                Resources.strings.authors_pictures),
            currentIndex = 0
        )
    )

    val tabState: StateFlow<FaithTabState> = _tabState.asStateFlow()

    val faithUiState: StateFlow<FaithUiState> = getFaithByNameUseCase.faithUiStateStream(
        name = name,
        tabState = tabState
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = FaithUiState.Loading
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

private fun GetFaithByNameUseCase.faithUiStateStream(
    name: String,
    tabState: StateFlow<FaithTabState>
): Flow<FaithUiState> {
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

        FaithUiState.Success(userFaith = it)
    }
}

data class FaithTabState(
    val titles: MutableList<StringResource>,
    val currentIndex: Int
)

sealed interface FaithUiState {
    data class Success(val userFaith: UserFaith) : FaithUiState
    object Loading : FaithUiState
}