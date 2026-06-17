package com.onelittleangel.domain.theme

import com.onelittleangel.data.source.ThemeRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.model.UserTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetMainThemesUseCase(
    private val themeRepository: ThemeRepository,
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(): Flow<List<UserTheme>> {
        return combine(
            userDataRepository.userData,
            themeRepository.mainThemes()
        ) { userData, themes ->
            themes.map { UserTheme(theme = it, userData = userData) }
        }
    }
}