package com.onelittleangel.data.source

import com.onelittleangel.cache.model.CachedQuote
import com.onelittleangel.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    fun quoteById(idQuote: String): Flow<Quote>

    fun quotesByIds(ids: List<String>): Flow<List<Quote>>

    fun quotesByIdAuthor(idAuthor: String): Flow<List<Quote>>

    fun quotesByAuthor(name: String): Flow<List<Quote>>

    fun quotesByIdBook(idBook: String): Flow<List<Quote>>

    fun quotesByBook(name: String): Flow<List<Quote>>

    fun quotesByIdMovement (idMovement: String): Flow<List<Quote>>

    fun quotesByMovement(name: String): Flow<List<Quote>>

    fun quotesByIdTheme(idTheme: String): Flow<List<Quote>>

    fun quotesByTheme(name: String): Flow<List<Quote>>

    fun allQuotes(): Flow<List<Quote>>
}