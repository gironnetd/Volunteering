package com.onelittleangel.domain.book

import com.onelittleangel.data.source.BookRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.model.UserBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetBooksUseCase(
    private val bookRepository: BookRepository,
    private val userDataRepository: UserDataRepository
) {

    operator fun invoke(): Flow<List<UserBook>> {
        return combine(
            userDataRepository.userData,
            bookRepository.allBooks()
        ) { userData, books ->
            books///.filter { it.language.subSequence(0, 2) == Locale.current.language }
                //.sortedBy { it.name }
                .map { UserBook(book = it, userData = userData) }
        }
    }
}