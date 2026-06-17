package com.onelittleangel.domain.book

import com.onelittleangel.data.source.BookRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.model.UserBook
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map

class GetBookByNameUseCase(
    private val bookRepository: BookRepository,
    private val userDataRepository: UserDataRepository,
    private val olaPreferencesDataSource: OlaPreferencesDataSource
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(name: String): Flow<UserBook> {
        return combine(
            olaPreferencesDataSource.userDataStream
        ) { userData ->
            bookRepository.bookByName(name = name).map  { book ->
                UserBook(book = book, userData = userData.first())
            }
        }.flattenConcat()
    }
}