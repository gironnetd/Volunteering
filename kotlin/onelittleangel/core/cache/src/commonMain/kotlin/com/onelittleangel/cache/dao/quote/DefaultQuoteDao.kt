package com.onelittleangel.cache.dao.quote

import androidx.compose.ui.text.intl.Locale
import com.onelittleangel.cache.model.CachedQuote
import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class DefaultQuoteDao(private val realm: Realm) : QuoteDao {

    override fun quoteById(idQuote: String): Flow<CachedQuote> {
        return flowOf(realm.query(CachedQuote::class, query = "idQuote == $0", idQuote).find().first())
    }

    override fun quotesByIds(ids: List<String>): Flow<List<CachedQuote>> {
        return flowOf(realm.query(CachedQuote::class).find().toList().filter { ids.contains(it.idQuote) })
    }

    override fun quotesByIdAuthor(idAuthor: String): Flow<List<CachedQuote>> = flow {
        realm.query(CachedQuote::class, query = "idAuthor == $0", idAuthor)
    }

    override fun quotesByAuthor(name: String): Flow<List<CachedQuote>> = flow {
        realm.query(CachedQuote::class, query = "name == $0", name)
    }

    override fun quotesByIdBook(idBook: String): Flow<List<CachedQuote>> = flow {
        realm.query(CachedQuote::class, query = "idBook == $0", idBook)
    }

    override fun quotesByBook(name: String): Flow<List<CachedQuote>> = flow {
        realm.query(CachedQuote::class, query = "name == $0", name)
    }

    override fun quotesByIdMovement(idMovement: String): Flow<List<CachedQuote>> = flow {
        realm.query(CachedQuote::class, query = "idMovement == $0", idMovement)
    }

    override fun quotesByMovement(name: String): Flow<List<CachedQuote>> = flow {
        realm.query(CachedQuote::class, query = "name == $0", name)
    }

    override fun quotesByIdTheme(idTheme: String): Flow<List<CachedQuote>> = flow {
        realm.query(CachedQuote::class, query = "idTheme == $0", idTheme)
    }

    override fun quotesByTheme(name: String): Flow<List<CachedQuote>> = flow {
        realm.query(CachedQuote::class, query = "name == $0", name)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun allQuotes(): Flow<List<CachedQuote>> = channelFlow {
        launch((Dispatchers.IO.limitedParallelism(60))) {
            realm.query(CachedQuote::class, query = "language CONTAINS $0", Locale.current.language)
                .asFlow().collect { changes: ResultsChange<CachedQuote> ->
                    when (changes) {
                        is InitialResults -> send(changes.list.shuffled().subList(0, 1))
                        else -> {}
                    }
                }
        }
    }
}