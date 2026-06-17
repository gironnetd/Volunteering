package com.onelittleangel.domain.theme

import com.onelittleangel.data.source.ThemeRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.model.UserTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetThemeByIdUseCase(
    private val themeRepository: ThemeRepository,
    private val userDataRepository: UserDataRepository
) {

    operator fun invoke(idTheme: String): Flow<UserTheme> {
        return combine(
            userDataRepository.userData,
            themeRepository.themeById(idTheme = idTheme)
        ) { userData, theme ->
            UserTheme(theme = theme, userData = userData)
        }
    }
}