package com.onelittleangel.cache.dao.biography

import androidx.compose.ui.text.intl.Locale
import com.onelittleangel.cache.model.CachedPresentation
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

class DefaultBiographyDao(private val realm: Realm) : BiographyDao {

    override fun biographyById(idBiography: String): Flow<CachedPresentation> {
       return flowOf(realm.query(CachedPresentation::class, query = "idPresentation == $0", idBiography).find().first())
    }

    override fun biographiesByIds(ids: List<String>): Flow<List<CachedPresentation>> {
       return flowOf(realm.query(CachedPresentation::class).find().toList().filter { ids.contains(it.idPresentation) })
    }

    override fun biographyByIdAuthor(idAuthor: String): Flow<CachedPresentation> = flow {
        realm.query(CachedPresentation::class, query = "idAuthor == $0", idAuthor)
    }

    override fun biographyByIdBook(idBook: String): Flow<CachedPresentation> = flow {
        realm.query(CachedPresentation::class, query = "idBook == $0", idBook)
    }

    override fun biographyByIdFaith(idFaith: String): Flow<CachedPresentation> = flow {
        realm.query(CachedPresentation::class, query = "idFaith == $0", idFaith)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun allBiographies(): Flow<List<CachedPresentation>> = channelFlow {
        launch((Dispatchers.IO.limitedParallelism(60))) {
            realm.query(CachedPresentation::class, query = "language CONTAINS $0", Locale.current.language)
                .asFlow().collect { changes: ResultsChange<CachedPresentation> ->
                    when (changes) {
                        is InitialResults -> send(changes.list.shuffled().subList(0, 1))
                        else -> {}
                    }
                }
        }
    }
}