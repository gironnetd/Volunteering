package com.onelittleangel.model

data class UserPicture internal constructor(
    val idPicture: String,
    val idData: Long,
    val nameSmall: String,
    val extension: String,
    val comments: Map<String, String>?,
    val width : Long,
    val height : Long,
    val portrait : Boolean,
    val picture: ByteArray?,
) : UserResource(followableTopics = emptyList(), isSaved = false) {
    constructor(picture: Picture, userData: UserData) : this(
        idPicture = picture.idPicture,
        idData = picture.idData,
        nameSmall = picture.nameSmall,
        extension = picture.extension,
        comments = picture.comments,
        picture = picture.picture,
        width = picture.width,
        height = picture.height,
        portrait = picture.portrait
    ) {
        followableTopics = picture.topics.map {
            FollowableTopic(
                topic = it,
                isFollowed = userData.bookmarkedResources.contains(it.id)
            )
        }
        isSaved = userData.bookmarkedResources.contains(picture.idPicture)
    }
}
