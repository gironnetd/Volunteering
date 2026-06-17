package com.onelittleangel.cache.dao.author

import androidx.compose.ui.text.intl.Locale
import com.onelittleangel.cache.model.CachedAuthor
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

class DefaultAuthorDao(private val realm: Realm) : AuthorDao {

    override fun authorById(idAuthor: String): Flow<CachedAuthor> {
        return flowOf(realm.query(CachedAuthor::class, query = "idAuthor == $0", idAuthor).find().first())
    }

    override fun authorByName(name: String): Flow<CachedAuthor> {
        return flowOf(realm.query(CachedAuthor::class, query = "name == $0", name).find().first())
    }

    override fun authorsByIdMovement(idMovement: String): Flow<List<CachedAuthor>> = flow {
        realm.query(CachedAuthor::class, query = "idMovement == $0", idMovement)
    }

    override fun authorsByIdTheme(idTheme: String): Flow<List<CachedAuthor>> = flow {
        realm.query(CachedAuthor::class, query = "idTheme == $0", idTheme)
    }

    override fun authorByIdPresentation(idPresentation: String): Flow<List<CachedAuthor>> = flow {
        realm.query(CachedAuthor::class, query = "presentation.idPresentation == $0", idPresentation)
    }

    override fun authorByIdPicture(idPicture: String): Flow<CachedAuthor> = flow {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun allAuthors(): Flow<List<CachedAuthor>> = channelFlow {
        launch((Dispatchers.IO.limitedParallelism(60))) {
            realm.query(CachedAuthor::class, query = "language CONTAINS $0", Locale.current.language)
                .asFlow().collect { changes: ResultsChange<CachedAuthor> ->
                    when (changes) {
                        is InitialResults -> send(
                            changes.list.copyFromRealm()
                                .onEach { author ->
                                    author.quotes.removeAll(author.quotes)
                                    author.pictures.removeAll(author.pictures)
                                }
                                .sortedBy { it.name.unaccent() })
                        else -> {}
                    }
                }
        }
    }
}