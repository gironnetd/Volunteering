package com.onelittleangel.cache.model

import com.onelittleangel.model.Biography
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedPresentation : RealmObject {

    @PrimaryKey
    var idPresentation: String = ""
    var language: String = ""
    var presentation: String? = null
    var presentationTitle1: String? = null
    var presentation1: String? = null
    var presentationTitle2: String? = null
    var presentation2: String? = null
    var presentationTitle3: String? = null
    var presentation3: String? = null
    var presentationTitle4: String? = null
    var presentation4: String? = null
    var sourcePresentation: String? = null
    var topics: RealmList<CachedTopic> = realmListOf()
}

fun CachedPresentation.asExternalModel() = Biography(
    idPresentation = idPresentation,
    language = language,
    presentation = presentation,
    presentationTitle1 = presentationTitle1,
    presentation1 = presentation1,
    presentationTitle2 = presentationTitle2,
    presentation2 = presentation2,
    presentationTitle3 = presentationTitle3,
    presentation3 = presentation3,
    presentationTitle4 = presentationTitle4,
    presentation4 = presentation4,
    sourcePresentation = sourcePresentation,
    topics = topics.map { it.asExternalModel() }
)
