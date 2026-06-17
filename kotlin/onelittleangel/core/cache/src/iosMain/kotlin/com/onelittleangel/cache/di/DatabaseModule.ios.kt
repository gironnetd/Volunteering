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
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual val databaseModule = module {
    single {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                CachedAuthor::class, CachedBook::class, CachedBookmark::class,
                CachedBookmarkGroup::class, CachedCentury::class, CachedMovement::class,
                CachedPicture::class, CachedPresentation::class, CachedQuote::class,
                CachedTheme::class, CachedTopic::class, CachedUrl::class, CachedUser::class))
            .name("default.realm")
            .directory(
                NSFileManager.defaultManager.URLForDirectory(
                    NSDocumentDirectory,
                    NSUserDomainMask,
                    null,
                    true,
                    null
                )?.path!! + "/realm"
            )
            .schemaVersion(3)
            .build()

        return@single Realm.open(config)
    }
}
