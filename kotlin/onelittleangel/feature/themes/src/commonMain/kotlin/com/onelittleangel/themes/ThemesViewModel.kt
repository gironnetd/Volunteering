package com.onelittleangel.themes

import com.onelittleangel.domain.theme.GetThemesUseCase
import com.onelittleangel.model.UserTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ThemesViewModel(
    getThemesUseCase: GetThemesUseCase
): ViewModel() {

    val themesUiState: StateFlow<ThemesUiState> = getThemesUseCase.themesUiStateStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ThemesUiState.Loading
        )
}

private fun GetThemesUseCase.themesUiStateStream(): Flow<ThemesUiState> {
    // Observe the followed authors, as they could change over time.
    return this().map {
        ThemesUiState.Success(userThemes = it)
    }
}

sealed interface ThemesUiState {
    data class Success(val userThemes: List<UserTheme>) : ThemesUiState
    object Loading : ThemesUiState
}