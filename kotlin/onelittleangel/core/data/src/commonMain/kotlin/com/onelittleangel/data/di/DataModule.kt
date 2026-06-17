package com.onelittleangel.data.di

import com.onelittleangel.cache.di.cacheModule
import com.onelittleangel.data.repository.DefaultAuthorRepository
import com.onelittleangel.data.repository.DefaultBiographyRepository
import com.onelittleangel.data.repository.DefaultBookRepository
import com.onelittleangel.data.repository.DefaultBookmarkRepository
import com.onelittleangel.data.repository.DefaultCenturyRepository
import com.onelittleangel.data.repository.DefaultFaithRepository
import com.onelittleangel.data.repository.DefaultPictureRepository
import com.onelittleangel.data.repository.DefaultQuoteRepository
import com.onelittleangel.data.repository.DefaultThemeRepository
import com.onelittleangel.data.repository.DefaultUrlRepository
import com.onelittleangel.data.repository.DefaultUserDataRepository
import com.onelittleangel.data.repository.DefaultUserRepository
import com.onelittleangel.data.source.AuthorRepository
import com.onelittleangel.data.source.BiographyRepository
import com.onelittleangel.data.source.BookRepository
import com.onelittleangel.data.source.BookmarkRepository
import com.onelittleangel.data.source.CenturyRepository
import com.onelittleangel.data.source.FaithRepository
import com.onelittleangel.data.source.PictureRepository
import com.onelittleangel.data.source.QuoteRepository
import com.onelittleangel.data.source.ThemeRepository
import com.onelittleangel.data.source.UrlRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.data.source.UserRepository
import com.onelittleangel.datastore.di.datastoreModule
import com.onelittleangel.model.ThemeBrand
import com.onelttleangel.remote.di.remoteModule
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    includes(cacheModule, remoteModule, datastoreModule)
    singleOf(::DefaultAuthorRepository) { bind<AuthorRepository>() }
    singleOf(::DefaultBookRepository) { bind<BookRepository>() }
    singleOf(::DefaultCenturyRepository) { bind<CenturyRepository>() }
    singleOf(::DefaultFaithRepository) { bind<FaithRepository>() }
    singleOf(::DefaultPictureRepository) { bind<PictureRepository>() }
    singleOf(::DefaultQuoteRepository) { bind<QuoteRepository>() }
    singleOf(::DefaultThemeRepository) { bind<ThemeRepository>() }
    singleOf(::DefaultUrlRepository) { bind<UrlRepository>() }
    singleOf(::DefaultUserRepository) { bind<UserRepository>() }
    singleOf(::DefaultUserDataRepository) { bind<UserDataRepository>() }
    singleOf(::DefaultBiographyRepository) { bind<BiographyRepository>() }
    singleOf(::DefaultBookmarkRepository) { bind<BookmarkRepository>() }
}

class Test: KoinComponent {
    val userDataRepository: UserDataRepository by inject()

    init {
        runBlocking {
            userDataRepository.setThemeBrand(ThemeBrand.quaternary)
            userDataRepository.userData.collect {
                println(it)
            }
        }
    }
}

