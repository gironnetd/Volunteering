package com.onelittleangel.faiths.di

import com.onelittleangel.cache.di.cacheModule
import com.onelittleangel.data.di.dataModule
import com.onelittleangel.data.repository.DefaultBookmarkRepository
import com.onelittleangel.datastore.di.datastoreModule
import com.onelittleangel.domain.di.domainModule
import com.onelittleangel.domain.faith.GetFaithByNameUseCase
import com.onelittleangel.faiths.FaithViewModel
import com.onelittleangel.faiths.FaithsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val faithsModule = module {
    includes(domainModule, dataModule, datastoreModule, cacheModule)

    factory { (name: String) ->
        FaithViewModel(
            name = name,
            getFaithByNameUseCase = GetFaithByNameUseCase(get(), get(), get()),
            bookmarkRepository = DefaultBookmarkRepository(get(), get())
        )
    }

    singleOf(::FaithsViewModel)
    //viewModelOf(::AuthorViewModel)
}
