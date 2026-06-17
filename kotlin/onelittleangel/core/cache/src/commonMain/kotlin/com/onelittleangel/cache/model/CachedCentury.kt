package com.onelittleangel.cache.model

import com.onelittleangel.model.Century
import io.realm.kotlin.ext.realmDictionaryOf
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmDictionary
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedCentury : RealmObject {

    @PrimaryKey
    var idCentury: String = ""
    var century: String = ""
    var frenchShortDescription: String? = null
    var englishShortDescription: String? = null
    var frenchPresentation: String = ""
    var englishPresentation: String = ""
    var presentations: RealmDictionary<String> = realmDictionaryOf()
    var topics: RealmList<CachedTopic> = realmListOf()
}

fun CachedCentury.asExternalModel() = Century(
    idCentury = idCentury,
    century = century,
    topics = topics.map { it.asExternalModel() }
)
