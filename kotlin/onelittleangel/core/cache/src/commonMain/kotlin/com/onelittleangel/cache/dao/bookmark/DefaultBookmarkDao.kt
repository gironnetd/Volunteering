package com.onelittleangel.cache.dao.bookmark

import com.onelittleangel.cache.model.CachedBookmark
import com.onelittleangel.cache.model.CachedBookmarkGroup
import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import io.realm.kotlin.query.find
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class DefaultBookmarkDao(private val realm: Realm) : BookmarkDao {

    override fun allBookmarks(): Flow<List<CachedBookmark>> {
        return flowOf(realm.query(CachedBookmark::class).find().toList())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun allBookmarkGroups(): Flow<List<CachedBookmarkGroup>> = channelFlow {
        launch((Dispatchers.IO.limitedParallelism(60))) {
            realm.query(CachedBookmarkGroup::class).asFlow().collect { changes: ResultsChange<CachedBookmarkGroup> ->
                when (changes) {
                    is UpdatedResults -> {
                        send(changes.list)
                    }
                    else -> {}
                }
            }
        }

        send(realm.query(CachedBookmarkGroup::class).find().toList())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun groupById(id: String): Flow<CachedBookmarkGroup> = channelFlow {
        launch((Dispatchers.IO.limitedParallelism(60))) {
            realm.query(CachedBookmarkGroup::class, query = "id == $0", id).asFlow().collect { changes: ResultsChange<CachedBookmarkGroup> ->
                when (changes) {
                    is UpdatedResults -> {
                        if(changes.list.isNotEmpty()) {
                            send(changes.list.first())
                        }
                    }
                    else -> {}
                }
            }
        }

        realm.query(CachedBookmarkGroup::class, query = "id == $0", id).find {
            if(it.isNotEmpty()) {
                launch {
                    send(it.first())
                }
            }
        }
    }

    override suspend fun updateResourceBookmarked(bookmark: CachedBookmark, bookmarked: Boolean) {
        realm.write {
            if(bookmarked) {
                copyToRealm(bookmark)
                realm.query(CachedBookmarkGroup::class, query = "id == $0", bookmark.idBookmarkGroup).first().find {
                    it?.let { group ->
                        findLatest(group)?.let {
                            it.bookmarks.add(bookmark)
                        }
                    }

                }
            } else {
                realm.query(CachedBookmark::class, query = "idResource == $0", bookmark.idResource).first().find {
                    it?.let { bookmark ->
                        findLatest(bookmark)?.let {
                            delete(it)
                        }
                    }
                }
            }
        }
    }

    override suspend fun createOrUpdate(group: CachedBookmarkGroup) {
        realm.write {
            copyToRealm(group)
        }
    }

    override suspend fun remove(group: CachedBookmarkGroup) {
        TODO("Not yet implemented")
    }

    override suspend fun update(bookmark: CachedBookmark) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBookmarkGroups(ids: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertBookmarkGroups(groups: List<CachedBookmarkGroup>) {
        TODO("Not yet implemented")
    }
}