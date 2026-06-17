package com.onelittleangel.domain.theme

import com.onelittleangel.data.source.ThemeRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.model.UserTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetThemesUseCase(
    private val themeRepository: ThemeRepository,
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(): Flow<List<UserTheme>> {
        return combine(
            userDataRepository.userData,
            themeRepository.allThemes()
        ) { userData, themes ->
            themes//.filter { it.language.subSequence(0, 2) == Locale.current.language }
                //.sortedBy { it.name }
                .map { UserTheme(theme = it, userData = userData) }
        }
    }
}