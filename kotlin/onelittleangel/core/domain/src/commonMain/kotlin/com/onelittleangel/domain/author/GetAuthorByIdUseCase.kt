package com.onelittleangel.domain.author

import com.onelittleangel.data.source.AuthorRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.model.UserAuthor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetAuthorByIdUseCase(
    private val authorRepository: AuthorRepository,
    private val userDataRepository: UserDataRepository
) {

    operator fun invoke(idAuthor: String): Flow<UserAuthor> {
        return combine(
            userDataRepository.userData,
            authorRepository.authorById(idAuthor = idAuthor)
            ) { userData, author ->
                UserAuthor(author = author, userData = userData)
            }
    }
}