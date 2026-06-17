package com.onelittleangel.model

open class UserResource internal constructor(
    var followableTopics: List<FollowableTopic>? = emptyList(),
    var isSaved: Boolean
) {
}

//public var id: String { uid }
//public var uid: String
//public var followableTopics: [FollowableTopic] = []
//@Published public var isSaved: BoolObservable = .init(value: false)