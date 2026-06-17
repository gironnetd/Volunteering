package com.onelittleangel.cache.model

import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.ResourceKind
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Instant

open class CachedBookmark()  : RealmObject {

    @PrimaryKey
    var id: String = ""
    var idBookmarkGroup: String = ""
    var note: String? = null
    var idResource: String = ""
    var kind: String = ""
    var dateCreation: RealmInstant = RealmInstant.now()

    constructor(
        id: String = "",
        idBookmarkGroup: String = "",
        note: String? = null,
        idResource: String = "",
        kind: String = "",
        dateCreation: RealmInstant = RealmInstant.now()
    ) : this() {
        this.id = id
        this.idBookmarkGroup = idBookmarkGroup
        this.note = note
        this.idResource = idResource
        this.kind = kind
        this.dateCreation = dateCreation
    }
}

fun CachedBookmark.asExternalModel() = Bookmark(
    id = id,
    idBookmarkGroup = idBookmarkGroup,
    note = note ?: "",
    idResource = idResource,
    kind = ResourceKind.valueOf(kind),
    dateCreation = dateCreation.toInstant()
)

fun Bookmark.asCached() = CachedBookmark(
    id = id,
    idBookmarkGroup = idBookmarkGroup,
    note = note,
    idResource = idResource,
    kind = kind.name,
    dateCreation = dateCreation.toRealmInstant()
)

fun RealmInstant.toInstant(): Instant {
    val sec: Long = this.epochSeconds
    // The value always lies in the range `-999_999_999..999_999_999`.
    // minus for timestamps before epoch, positive for after
    val nano: Int = this.nanosecondsOfSecond
    return if (sec >= 0) { // For positive timestamps, conversion can happen directly
        Instant.fromEpochSeconds(sec, nano.toLong())
    } else {
        // For negative timestamps, RealmInstant starts from the higher value with negative
        // nanoseconds, while Instant starts from the lower value with positive nanoseconds
        // TODO This probably breaks at edge cases like MIN/MAX
        Instant.fromEpochSeconds(sec - 1, 1_000_000 + nano.toLong())
    }
}
fun Instant.toRealmInstant(): RealmInstant {
    val sec: Long = this.epochSeconds
    // The value is always positive and lies in the range `0..999_999_999`.
    val nano: Int = this.nanosecondsOfSecond
    return if (sec >= 0) { // For positive timestamps, conversion can happen directly
        RealmInstant.from(sec, nano)
    } else {
        // For negative timestamps, RealmInstant starts from the higher value with negative
        // nanoseconds, while Instant starts from the lower value with positive nanoseconds
        // TODO This probably breaks at edge cases like MIN/MAX
        RealmInstant.from(sec + 1, -1_000_000 + nano)
    }
}
