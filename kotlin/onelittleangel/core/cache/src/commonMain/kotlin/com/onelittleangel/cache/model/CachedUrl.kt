package com.onelittleangel.cache.model

import com.onelittleangel.model.Url
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedUrl : RealmObject {

    @PrimaryKey
    var idUrl: String = ""
    var idSource: String = ""
    var title: String? = null
    var url: String = ""
    var presentation: String? = null
}

fun CachedUrl.asExternalModel() = Url(
    idUrl = idUrl,
    idSource = idSource,
    title = title,
    url = url,
    presentation = presentation
)
