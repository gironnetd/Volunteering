package com.onelittleangel.cache.di

import com.onelittleangel.cache.dao.author.AuthorDao
import com.onelittleangel.cache.dao.author.DefaultAuthorDao
import com.onelittleangel.cache.dao.biography.BiographyDao
import com.onelittleangel.cache.dao.biography.DefaultBiographyDao
import com.onelittleangel.cache.dao.book.BookDao
import com.onelittleangel.cache.dao.book.DefaultBookDao
import com.onelittleangel.cache.dao.bookmark.BookmarkDao
import com.onelittleangel.cache.dao.bookmark.DefaultBookmarkDao
import com.onelittleangel.cache.dao.century.CenturyDao
import com.onelittleangel.cache.dao.century.DefaultCenturyDao
import com.onelittleangel.cache.dao.faith.DefaultFaithDao
import com.onelittleangel.cache.dao.faith.FaithDao
import com.onelittleangel.cache.dao.picture.DefaultPictureDao
import com.onelittleangel.cache.dao.picture.PictureDao
import com.onelittleangel.cache.dao.quote.DefaultQuoteDao
import com.onelittleangel.cache.dao.quote.QuoteDao
import com.onelittleangel.cache.dao.theme.DefaultThemeDao
import com.onelittleangel.cache.dao.theme.ThemeDao
import com.onelittleangel.cache.dao.url.DefaultUrlDao
import com.onelittleangel.cache.dao.url.UrlDao
import com.onelittleangel.cache.dao.user.DefaultUserDao
import com.onelittleangel.cache.dao.user.UserDao
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val cacheModule = module {
    includes(databaseModule)
    singleOf(::DefaultAuthorDao) { bind<AuthorDao>() }
    singleOf(::DefaultBookDao) { bind<BookDao>() }
    singleOf(::DefaultCenturyDao) { bind<CenturyDao>() }
    singleOf(::DefaultFaithDao) { bind<FaithDao>() }
    singleOf(::DefaultPictureDao) { bind<PictureDao>() }
    singleOf(::DefaultQuoteDao) { bind<QuoteDao>() }
    singleOf(::DefaultThemeDao) { bind<ThemeDao>() }
    singleOf(::DefaultUrlDao) { bind<UrlDao>() }
    singleOf(::DefaultUserDao) { bind<UserDao>() }
    singleOf(::DefaultBiographyDao) { bind<BiographyDao>() }
    singleOf(::DefaultBookmarkDao) { bind<BookmarkDao>() }
}

