package com.onelittleangel.cache.model

import com.onelittleangel.model.User
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedUser : RealmObject {

    @PrimaryKey
    var id: String = ""
    var providerID: String = ""
    var uidUser: String? = null
    var email: String? = null
    var displayName: String? = null
    var photo: ByteArray? = null
}

fun CachedUser.asExternalModel() = User(
    id = id,
    providerID = providerID,
    uidUser = uidUser,
    email = email,
    displayName = displayName
)
