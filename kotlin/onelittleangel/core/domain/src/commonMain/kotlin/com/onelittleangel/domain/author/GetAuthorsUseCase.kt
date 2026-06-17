package com.onelittleangel.domain.author

import com.onelittleangel.data.source.AuthorRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.model.UserAuthor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetAuthorsUseCase(
    private val authorRepository: AuthorRepository,
    private val userDataRepository: UserDataRepository
) {

    operator fun invoke(): Flow<List<UserAuthor>> {
        return combine(
            userDataRepository.userData,
            authorRepository.allAuthors()
        ) { userData, authors ->
            authors//.filter { it.language.subSequence(0, 2) == Locale.current.language }
                .map { UserAuthor(author = it, userData = userData) }
        }
    }
}