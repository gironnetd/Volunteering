package com.onelittleangel.cache.dao.faith

import androidx.compose.ui.text.intl.Locale
import com.onelittleangel.cache.model.CachedMovement
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

class DefaultFaithDao(private val realm: Realm) : FaithDao {

    override fun faithById(idBiography: String): Flow<CachedMovement> {
        return flowOf(realm.query(CachedMovement::class, query = "idMovement == $0", idBiography).find().first())
    }

    override fun faithByName(name: String): Flow<CachedMovement>  {
        return flowOf(realm.query(CachedMovement::class, query = "name == $0", name).find().first())
    }

    override fun faithsByIdParent(idParent: String): Flow<List<CachedMovement>> = flow {
        realm.query(CachedMovement::class, query = "idParentMovement == $0", idParent)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun mainFaiths(): Flow<List<CachedMovement>> = channelFlow {
        launch(Dispatchers.IO.limitedParallelism(60)) {
            realm.query(CachedMovement::class, query = "language CONTAINS $0 AND idParentMovement == $1",
                Locale.current.language, null)
                .asFlow().collect { changes: ResultsChange<CachedMovement> ->
                when (changes) {
                    is InitialResults -> {
                        send(
                            changes.list.copyFromRealm().onEach { faith ->
                                //faith.movements.removeAll(faith.movements)
                                faith.authors.removeAll(faith.authors)
                                faith.books.removeAll(faith.books)
                            }.sortedBy { it.name.unaccent() }
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun allFaiths(): Flow<List<CachedMovement>> = channelFlow {
        launch(Dispatchers.IO.limitedParallelism(60)) {
            realm.query(CachedMovement::class, query = "language CONTAINS $0",
                Locale.current.language)
                .asFlow().collect { changes: ResultsChange<CachedMovement> ->
                    when (changes) {
                        is InitialResults -> {
                            send(
                                changes.list.copyFromRealm().onEach { faith ->
                                    //faith.movements.removeAll(faith.movements)
                                    faith.authors.removeAll(faith.authors)
                                    faith.books.removeAll(faith.books)
                                }.sortedBy { it.name.unaccent() }
                            )
                        }
                        else -> {}
                    }
                }
        }
    }
}