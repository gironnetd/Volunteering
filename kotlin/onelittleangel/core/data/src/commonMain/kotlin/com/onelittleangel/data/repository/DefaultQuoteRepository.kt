package com.onelittleangel.data.repository

import com.onelittleangel.cache.dao.quote.QuoteDao
import com.onelittleangel.cache.model.CachedQuote
import com.onelittleangel.cache.model.asExternalModel
import com.onelittleangel.data.source.QuoteRepository
import com.onelittleangel.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultQuoteRepository(private val quoteDao: QuoteDao) : QuoteRepository {

    override fun quoteById(idQuote: String): Flow<Quote> {
        return quoteDao.quoteById(idQuote).map { it.asExternalModel() }
    }

    override fun quotesByIds(ids: List<String>): Flow<List<Quote>> {
        return quoteDao.quotesByIds(ids).map { it.map(CachedQuote::asExternalModel) }
    }

    override fun quotesByIdAuthor(idAuthor: String): Flow<List<Quote>> {
        return quoteDao.quotesByIdAuthor(idAuthor).map { it.map(CachedQuote::asExternalModel) }
    }

    override fun quotesByAuthor(name: String): Flow<List<Quote>> {
        return quoteDao.quotesByAuthor(name).map { it.map(CachedQuote::asExternalModel) }
    }

    override fun quotesByIdBook(idBook: String): Flow<List<Quote>> {
        return quoteDao.quotesByIdBook(idBook).map { it.map(CachedQuote::asExternalModel) }
    }

    override fun quotesByBook(name: String): Flow<List<Quote>> {
        return quoteDao.quotesByBook(name).map { it.map(CachedQuote::asExternalModel) }
    }

    override fun quotesByIdMovement(idMovement: String): Flow<List<Quote>> {
        return quoteDao.quotesByIdMovement(idMovement).map { it.map(CachedQuote::asExternalModel) }
    }

    override fun quotesByMovement(name: String): Flow<List<Quote>> {
        return quoteDao.quotesByMovement(name).map { it.map(CachedQuote::asExternalModel) }
    }

    override fun quotesByIdTheme(idTheme: String): Flow<List<Quote>> {
        return quoteDao.quotesByIdTheme(idTheme).map { it.map(CachedQuote::asExternalModel) }
    }

    override fun quotesByTheme(name: String): Flow<List<Quote>> {
        return quoteDao.quotesByTheme(name).map { it.map(CachedQuote::asExternalModel) }
    }

    override fun allQuotes(): Flow<List<Quote>> {
        return quoteDao.allQuotes().map { it.map(CachedQuote::asExternalModel) }
    }
}