package com.onelittleangel.cache.model

import com.onelittleangel.model.ResourceKind
import com.onelittleangel.model.Topic
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedTopic : RealmObject {
    @PrimaryKey
    var id: String = ""
    var language: String = ""
    var name: String = ""
    var shortDescription: String? = null
    var longDescription: String? = null
    var idResource: String = ""
    var kind: String = ""
}

fun CachedTopic.asExternalModel() = Topic(
    id = id,
    language = language,
    name = name,
    shortDescription = shortDescription,
    longDescription = longDescription,
    idResource = idResource,
    kind = ResourceKind.valueOf(kind)
)
