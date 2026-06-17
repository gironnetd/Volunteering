package com.onelittleangel.cache.model

import com.onelittleangel.model.Author
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedAuthor : RealmObject {

    @PrimaryKey
    var idAuthor: String = ""
    var name: String = ""
    var language: String = ""
    var idRelatedAuthors: RealmList<String> = realmListOf()
    var century: CachedCentury? = null
    var surname: String? = null
    var details: String? = null
    var period: String? = null
    var idMovement: String? = null
    var bibliography: String? = null
    var presentation: CachedPresentation? = null
    var mainPicture: Long? = null
    var mcc1: String? = null
    var quotes: RealmList<CachedQuote> = realmListOf()
    var pictures: RealmList<CachedPicture> = realmListOf()
    var urls: RealmList<CachedUrl> = realmListOf()
    var topics: RealmList<CachedTopic> = realmListOf()
}

fun CachedAuthor.asExternalModel() = Author(
    idAuthor = idAuthor,
    name = name,
    language = language,
    idRelatedAuthors = idRelatedAuthors,
    century = century?.asExternalModel(),
    surname = surname,
    details = details,
    period = period,
    idMovement = idMovement,
    bibliography = bibliography,
    biography = presentation?.asExternalModel(),
    mainPicture = mainPicture,
    mcc1 = mcc1,
    quotes = quotes.map { it.asExternalModel() },
    pictures = pictures.map { it.asExternalModel() },
    urls = urls.map { it.asExternalModel() },
    topics = topics.map { it.asExternalModel() }
)
