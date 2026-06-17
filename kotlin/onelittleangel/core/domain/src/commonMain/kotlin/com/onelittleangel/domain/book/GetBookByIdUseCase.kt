package com.onelittleangel.domain.book

import com.onelittleangel.data.source.BookRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.model.UserBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetBookByIdUseCase(
    private val bookRepository: BookRepository,
    private val userDataRepository: UserDataRepository
) {

    operator fun invoke(idBook: String): Flow<UserBook> {
        return combine(
            userDataRepository.userData,
            bookRepository.bookById(idBook = idBook)
        ) { userData, book ->
            UserBook(book = book, userData = userData)
        }
    }
}