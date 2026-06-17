package com.onelittleangel.faiths

import com.onelittleangel.domain.faith.GetFaithsUseCase
import com.onelittleangel.model.UserFaith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class FaithsViewModel(
    getFaithsUseCase: GetFaithsUseCase
): ViewModel() {

    val faithsUiState: StateFlow<FaithsUiState> = getFaithsUseCase.faithsUiStateStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = FaithsUiState.Loading
        )
}

private fun GetFaithsUseCase.faithsUiStateStream(): Flow<FaithsUiState> {
    // Observe the followed authors, as they could change over time.
    return this().map {
        FaithsUiState.Success(
            userFaiths = it/*.onEach { faith ->
                faith.faiths.toMutableList().removeAll(faith.faiths)
                faith.authors?.let { authors -> authors.toMutableList().removeAll(authors) }
                faith.books?.let { books -> books.toMutableList().removeAll(books) }
                faith.pictures?.let { pictures -> pictures.toMutableList().removeAll(pictures) }
            }*/
        )
    }
}

sealed interface FaithsUiState {
    data class Success(val userFaiths: List<UserFaith>) : FaithsUiState
    object Loading : FaithsUiState
}