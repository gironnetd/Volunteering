package com.onelittleangel.cache.dao.book

import androidx.compose.ui.text.intl.Locale
import com.onelittleangel.cache.model.CachedBook
import com.onelittleangel.common.unaccent
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.copyFromRealm
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

class DefaultBookDao(private val realm: Realm) : BookDao {

    override fun bookById(idBook: String): Flow<CachedBook> {
        return flowOf(realm.query(CachedBook::class, query = "idBook == $0", idBook).find().first())
    }

    override fun bookByName(name: String): Flow<CachedBook> {
        return flowOf(realm.query(CachedBook::class, query = "name == $0", name).find().first())
    }

    override fun booksByIdMovement(idMovement: String): Flow<List<CachedBook>> = flow {
        realm.query(CachedBook::class, query = "idMovement == $0", idMovement)
    }

    override fun booksByIdTheme(idTheme: String): Flow<List<CachedBook>> = flow {
        realm.query(CachedBook::class, query = "idTheme == $0", idTheme)
    }

    override fun bookByIdPresentation(idPresentation: String): Flow<CachedBook> = flow {
        realm.query(CachedBook::class, query = "presentation.idPresentation == $0", idPresentation)
    }

    override fun bookByIdPicture(idPicture: String): Flow<CachedBook> = flow {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun allBooks(): Flow<List<CachedBook>> = channelFlow {
        launch((Dispatchers.IO.limitedParallelism(60))) {
            realm.query(CachedBook::class, query = "language CONTAINS $0", Locale.current.language)
                .asFlow().collect { changes: ResultsChange<CachedBook> ->
                    when (changes) {
                        is InitialResults -> send(
                            changes.list.copyFromRealm()
                                .onEach {  book ->
                                    book.quotes.removeAll(book.quotes)
                                    book.pictures.removeAll(book.pictures)
                                }
                                .sortedBy { it.name.unaccent() }
                        )
                        else -> {}
                    }
                }
        }
    }
}