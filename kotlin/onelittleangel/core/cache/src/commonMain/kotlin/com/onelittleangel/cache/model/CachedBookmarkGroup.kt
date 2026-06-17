package com.onelittleangel.cache.model

import com.onelittleangel.model.BookmarkGroup
import com.onelittleangel.model.BookmarkGroupLocation
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedBookmarkGroup() : RealmObject {

    @PrimaryKey
    var id: String = ""
    var idParent: String? = null
    var location: String = ""
    var directoryName: String = ""
    var shortDescription: String? = null
    var groups: RealmList<CachedBookmarkGroup> = realmListOf()
    var bookmarks: RealmList<CachedBookmark> = realmListOf()

    constructor(
        id: String = "",
        idParent: String? = null,
        location: String = "",
        directoryName: String = "",
        shortDescription: String? = null,
        groups: RealmList<CachedBookmarkGroup> = realmListOf(),
        bookmarks: RealmList<CachedBookmark> = realmListOf()
    ) : this() {
        this.id = id
        this.idParent = idParent
        this.location = location
        this.directoryName = directoryName
        this.shortDescription = shortDescription
        this.groups = groups
        this.bookmarks = bookmarks
    }
}

fun CachedBookmarkGroup.asExternalModel(): BookmarkGroup {
    return BookmarkGroup(
        id = id,
        idParent = idParent,
        location = BookmarkGroupLocation.device,
        directoryName = directoryName,
        shortDescription = shortDescription ?: "",
        groups = groups.map { it.asExternalModel() },
        bookmarks = bookmarks.map { it.asExternalModel() }
    )
}

fun BookmarkGroup.asCached(): CachedBookmarkGroup {
    return CachedBookmarkGroup(
        id = id,
        idParent = idParent,
        location = location.name,
        directoryName = directoryName,
        shortDescription = shortDescription,
        groups = groups.map { it.asCached() }.toRealmList(),
        bookmarks = bookmarks.map { it.asCached() }.toRealmList()
    )
}
