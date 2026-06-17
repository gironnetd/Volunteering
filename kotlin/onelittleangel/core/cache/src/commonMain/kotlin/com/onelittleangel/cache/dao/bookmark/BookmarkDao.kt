package com.onelittleangel.cache.dao.bookmark

import com.onelittleangel.cache.model.CachedBookmark
import com.onelittleangel.cache.model.CachedBookmarkGroup
import kotlinx.coroutines.flow.Flow

interface BookmarkDao {

    fun allBookmarks(): Flow<List<CachedBookmark>>

    fun allBookmarkGroups(): Flow<List<CachedBookmarkGroup>>

    fun groupById(id: String): Flow<CachedBookmarkGroup>

    suspend fun updateResourceBookmarked(bookmark: CachedBookmark, bookmarked: Boolean)

    suspend fun createOrUpdate(group: CachedBookmarkGroup)

    suspend fun remove(group: CachedBookmarkGroup)

    suspend fun update(bookmark: CachedBookmark)

    suspend fun deleteBookmarkGroups(ids: List<String>)

    suspend fun upsertBookmarkGroups(groups: List<CachedBookmarkGroup>)
}