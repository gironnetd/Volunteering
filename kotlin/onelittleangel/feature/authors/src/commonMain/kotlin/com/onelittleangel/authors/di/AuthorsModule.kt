package com.onelittleangel.authors.di

import com.onelittleangel.authors.AuthorViewModel
import com.onelittleangel.authors.AuthorsViewModel
import com.onelittleangel.cache.di.cacheModule
import com.onelittleangel.data.di.dataModule
import com.onelittleangel.data.repository.DefaultBookmarkRepository
import com.onelittleangel.datastore.di.datastoreModule
import com.onelittleangel.domain.author.GetAuthorByNameUseCase
import com.onelittleangel.domain.di.domainModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authorsModule = module {
    includes(domainModule, dataModule, datastoreModule, cacheModule)

    factory { (name: String) ->
        AuthorViewModel(
            name = name,
            getAuthorByNameUseCase = GetAuthorByNameUseCase(get(), get(), get()),
            bookmarkRepository = DefaultBookmarkRepository(get(), get())
        )
    }

    singleOf(::AuthorsViewModel)
}