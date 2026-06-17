package com.onelittleangel.cache.model

import com.onelittleangel.model.Picture
import io.realm.kotlin.ext.realmDictionaryOf
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmDictionary
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedPicture : RealmObject {

    @PrimaryKey
    var idPicture: String = ""
    var idData: Long = 0
    var nameSmall: String = ""
    var extension: String = ""
    var comments: RealmDictionary<String> = realmDictionaryOf()
    var width: Long = 0
    var height: Long = 0
    var portrait: Boolean = false
    var picture: ByteArray? = null
    var topics: RealmList<CachedTopic> = realmListOf()
}

fun CachedPicture.asExternalModel() = Picture(
    idPicture = idPicture,
    idData = idData,
    nameSmall = nameSmall,
    extension = extension,
    comments = comments,
    width = width,
    height = height,
    portrait = portrait,
    picture = picture,
    topics = topics.map { it.asExternalModel() }
)
