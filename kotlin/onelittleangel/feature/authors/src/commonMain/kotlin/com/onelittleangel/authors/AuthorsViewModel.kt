package com.onelittleangel.authors

import com.onelittleangel.domain.author.GetAuthorsUseCase
import com.onelittleangel.domain.faith.GetMainFaithsUseCase
import com.onelittleangel.model.UserAuthor
import com.onelittleangel.model.UserFaith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class AuthorsViewModel(
    getAuthorsUseCase: GetAuthorsUseCase,
    getMainFaithsUseCase: GetMainFaithsUseCase
): ViewModel() {

    val authorsUiState: StateFlow<AuthorsUiState> = getAuthorsUseCase.authorsUiStateStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = AuthorsUiState.Loading
        )

    val faithsUiState: StateFlow<FaithsUiState> = getMainFaithsUseCase.faithsUiStateStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = FaithsUiState.Loading
        )
}

private fun GetMainFaithsUseCase.faithsUiStateStream(): Flow<FaithsUiState> {
    // Observe the followed authors, as they could change over time.
    return this().map {
        FaithsUiState.Success(userFaiths = it)
    }
}

private fun GetAuthorsUseCase.authorsUiStateStream(): Flow<AuthorsUiState> {
    // Observe the followed authors, as they could change over time.
    return this().map {
        AuthorsUiState.Success(userAuthors = it)
    }
}

sealed interface AuthorsUiState {
    data class Success(val userAuthors: List<UserAuthor>) : AuthorsUiState
    object Loading : AuthorsUiState
}

sealed interface FaithsUiState {
    data class Success(val userFaiths: List<UserFaith>) : FaithsUiState
    object Loading : FaithsUiState
}