package com.onelittleangel.data.source

import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.BookmarkGroup
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    fun allBookmarks(): Flow<List<Bookmark>>

    fun allBookmarkGroups(): Flow<List<BookmarkGroup>>

    fun groupById(id: String): Flow<BookmarkGroup>

    suspend fun updateResourceBookmarked(bookmark: Bookmark, bookmarked: Boolean)

    suspend fun createOrUpdate(group: BookmarkGroup)

    suspend fun remove(group: BookmarkGroup)

    suspend fun update(bookmark: Bookmark)

    suspend fun deleteBookmarkGroups(ids: List<String>)

    suspend fun upsertBookmarkGroups(groups: List<BookmarkGroup>)
}