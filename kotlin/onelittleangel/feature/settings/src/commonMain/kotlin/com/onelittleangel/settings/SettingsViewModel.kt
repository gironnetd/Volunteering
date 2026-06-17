package com.onelittleangel.settings

import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.model.DarkThemeConfig
import com.onelittleangel.model.ThemeBrand
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class SettingsViewModel(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {
    val settingsUiState: StateFlow<SettingsUiState> =
        userDataRepository.userData
            .map { userData ->
                SettingsUiState.Success(
                    settings = UserEditableSettings(
                        brand = userData.themeBrand,
                        darkThemeConfig = userData.darkThemeConfig
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                // Starting eagerly means the user data is ready when the SettingsDialog is laid out
                // for the first time. Without this, due to b/221643630 the layout is done using the
                // "Loading" text, then replaced with the user editable fields once loaded, however,
                // the layout height doesn't change meaning all the fields are squashed into a small
                // scrollable column.
                // TODO: Change to SharingStarted.WhileSubscribed(5_000) when b/221643630 is fixed
                started = SharingStarted.Eagerly,
                initialValue = SettingsUiState.Loading
            )

    fun updateThemeBrand(themeBrand: ThemeBrand) {
        viewModelScope.launch {
            userDataRepository.setThemeBrand(themeBrand)
        }
    }

    fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            userDataRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }
}

data class UserEditableSettings(val brand: ThemeBrand, val darkThemeConfig: DarkThemeConfig)

sealed interface SettingsUiState {
    object Loading : SettingsUiState
    data class Success(val settings: UserEditableSettings) : SettingsUiState
}