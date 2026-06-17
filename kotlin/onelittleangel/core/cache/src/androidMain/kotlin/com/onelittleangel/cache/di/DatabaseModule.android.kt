package com.onelittleangel.cache.di

import com.onelittleangel.cache.model.CachedAuthor
import com.onelittleangel.cache.model.CachedBook
import com.onelittleangel.cache.model.CachedBookmark
import com.onelittleangel.cache.model.CachedBookmarkGroup
import com.onelittleangel.cache.model.CachedCentury
import com.onelittleangel.cache.model.CachedMovement
import com.onelittleangel.cache.model.CachedPicture
import com.onelittleangel.cache.model.CachedPresentation
import com.onelittleangel.cache.model.CachedQuote
import com.onelittleangel.cache.model.CachedTheme
import com.onelittleangel.cache.model.CachedTopic
import com.onelittleangel.cache.model.CachedUrl
import com.onelittleangel.cache.model.CachedUser
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

actual val databaseModule = module {
    single {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                CachedAuthor::class, CachedBook::class, CachedBookmark::class,
                CachedBookmarkGroup::class, CachedCentury::class, CachedMovement::class,
                CachedPicture::class, CachedPresentation::class, CachedQuote::class,
                CachedTheme::class, CachedTopic::class, CachedUrl::class, CachedUser::class))
            .initialRealmFile(assetFile = "default.realm")
            .schemaVersion(3)
            .build()

        return@single Realm.open(config)
    }
}
