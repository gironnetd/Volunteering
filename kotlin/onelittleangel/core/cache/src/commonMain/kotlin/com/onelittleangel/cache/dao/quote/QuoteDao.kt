package com.onelittleangel.cache.dao.quote

import com.onelittleangel.cache.model.CachedQuote
import kotlinx.coroutines.flow.Flow

interface QuoteDao {

    fun quoteById(idQuote: String): Flow<CachedQuote>

    fun quotesByIds(ids: List<String>): Flow<List<CachedQuote>>

    fun quotesByIdAuthor(idAuthor: String): Flow<List<CachedQuote>>

    fun quotesByAuthor(name: String): Flow<List<CachedQuote>>

    fun quotesByIdBook(idBook: String): Flow<List<CachedQuote>>

    fun quotesByBook(name: String): Flow<List<CachedQuote>>

    fun quotesByIdMovement (idMovement: String): Flow<List<CachedQuote>>

    fun quotesByMovement(name: String): Flow<List<CachedQuote>>

    fun quotesByIdTheme(idTheme: String): Flow<List<CachedQuote>>

    fun quotesByTheme(name: String): Flow<List<CachedQuote>>

    fun allQuotes(): Flow<List<CachedQuote>>
}