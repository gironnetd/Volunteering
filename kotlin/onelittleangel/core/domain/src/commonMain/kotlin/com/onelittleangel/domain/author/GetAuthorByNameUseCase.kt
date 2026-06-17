package com.onelittleangel.domain.author

import com.onelittleangel.data.source.AuthorRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.model.UserAuthor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map

class GetAuthorByNameUseCase(
    private val authorRepository: AuthorRepository,
    private val userDataRepository: UserDataRepository,
    private val olaPreferencesDataSource: OlaPreferencesDataSource
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(name: String): Flow<UserAuthor> {
        return combine(
            olaPreferencesDataSource.userDataStream
        ) { userData ->
            authorRepository.authorsByName(name = name).map  { author ->
                UserAuthor(author = author, userData = userData.first())
            }
        }.flattenConcat()
    }
}