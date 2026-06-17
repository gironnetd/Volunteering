package com.onelittleangel.cache.model

import com.onelittleangel.model.Quote
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedQuote : RealmObject {

    @PrimaryKey
    var idQuote: String = ""
    var language: String = ""
    var quote: String = ""
    var source: String? = null
    var reference: String? = null
    var remarque: String? = null
    var comment: String? = null
    var commentName: String? = null
    var topics: RealmList<CachedTopic> = realmListOf()
}

fun CachedQuote.asExternalModel() = Quote(
    idQuote = idQuote,
    language = language,
    quote = quote,
    source = source,
    reference = reference,
    remarque = remarque,
    comment = comment,
    commentName = commentName,
    topics = topics.map { it.asExternalModel() }
)
