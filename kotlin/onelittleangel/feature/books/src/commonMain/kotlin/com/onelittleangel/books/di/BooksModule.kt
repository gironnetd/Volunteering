package com.onelittleangel.books.di

import com.onelittleangel.books.BookViewModel
import com.onelittleangel.books.BooksViewModel
import com.onelittleangel.cache.di.cacheModule
import com.onelittleangel.data.di.dataModule
import com.onelittleangel.data.repository.DefaultBookmarkRepository
import com.onelittleangel.datastore.di.datastoreModule
import com.onelittleangel.domain.book.GetBookByNameUseCase
import com.onelittleangel.domain.di.domainModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val booksModule = module {
    includes(domainModule, dataModule, datastoreModule, cacheModule)

    factory { (name: String) ->
        BookViewModel(
            name = name,
            getBookByNameUseCase = GetBookByNameUseCase(get(), get(), get()),
            bookmarkRepository = DefaultBookmarkRepository(get(), get())
        )
    }

    singleOf(::BooksViewModel)
    //viewModelOf(::AuthorViewModel)
}
