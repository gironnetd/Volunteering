package com.onelittleangel.model

data class UserBiography internal constructor(
    val idPresentation: String,
    val language: String,
    val presentation: String?,
    val presentationTitle1: String?,
    val presentation1: String?,
    val presentationTitle2: String?,
    val presentation2: String?,
    val presentationTitle3: String?,
    val presentation3: String?,
    val presentationTitle4: String?,
    val presentation4: String?,
    val sourcePresentation: String?
) : UserResource(followableTopics = emptyList(), isSaved = false) {
    constructor(biography: Biography, userData: UserData) : this(
        idPresentation = biography.idPresentation,
        language  = biography.language,
        presentation = biography.presentation,
        presentationTitle1 = biography.presentationTitle1,
        presentation1 = biography.presentation1,
        presentationTitle2 = biography.presentationTitle2,
        presentation2 = biography.presentation2,
        presentationTitle3 = biography.presentationTitle3,
        presentation3 = biography.presentation3,
        presentationTitle4 = biography.presentationTitle4,
        presentation4 = biography.presentation4,
        sourcePresentation = biography.sourcePresentation
        ) {
        followableTopics = biography.topics.map {
            FollowableTopic(
                topic = it,
                isFollowed = userData.bookmarkedResources.contains(it.id)
            )
        }
        isSaved = userData.bookmarkedResources.contains(biography.idPresentation)
    }
}

//public var language: String
//public var presentation: String?
//public var presentationTitle1: String?
//public var presentation1: String?
//public var presentationTitle2: String?
//public var presentation2: String?
//public var presentationTitle3: String?
//public var presentation3: String?
//public var presentationTitle4: String?
//public var presentation4: String?
//public var sourcePresentation: String?