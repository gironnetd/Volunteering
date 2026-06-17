package com.onelittleangel.data.repository

import com.onelittleangel.cache.dao.bookmark.BookmarkDao
import com.onelittleangel.cache.model.CachedBookmark
import com.onelittleangel.cache.model.CachedBookmarkGroup
import com.onelittleangel.cache.model.asCached
import com.onelittleangel.cache.model.asExternalModel
import com.onelittleangel.data.source.BookmarkRepository
import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.BookmarkGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookmarkRepository(
    private val bookmarkDao: BookmarkDao,
    private val olaPreferences: OlaPreferencesDataSource) : BookmarkRepository {

    override fun allBookmarks(): Flow<List<Bookmark>> {
        return bookmarkDao.allBookmarks().map { it.map(CachedBookmark::asExternalModel) }
    }

    override fun allBookmarkGroups(): Flow<List<BookmarkGroup>> {
        return bookmarkDao.allBookmarkGroups().map { it.map(CachedBookmarkGroup::asExternalModel) }
    }

    override fun groupById(id: String): Flow<BookmarkGroup> {
        return bookmarkDao.groupById(id).map { it.asExternalModel() }
    }

    override suspend fun updateResourceBookmarked(bookmark: Bookmark, bookmarked: Boolean) {
        bookmarkDao.updateResourceBookmarked(bookmark = bookmark.asCached(), bookmarked = bookmarked)

        if(bookmarked) {
            olaPreferences.setResourceBookmarked(resourceId = bookmark.idResource, bookmarked = bookmarked)
        } else {
            bookmarkDao.allBookmarks().collect { bookmarks ->
                if (bookmarks.none { it.idResource == bookmark.idResource }) {
                    olaPreferences.setResourceBookmarked(resourceId = bookmark.idResource, bookmarked = bookmarked)
                }
            }
        }
    }

    override suspend fun createOrUpdate(group: BookmarkGroup) {
        bookmarkDao.createOrUpdate(group = group.asCached())
    }

    override suspend fun remove(group: BookmarkGroup) {
        TODO("Not yet implemented")
    }

    override suspend fun update(bookmark: Bookmark) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBookmarkGroups(ids: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertBookmarkGroups(groups: List<BookmarkGroup>) {
        TODO("Not yet implemented")
    }
}