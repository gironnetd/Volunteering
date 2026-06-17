package com.onelittleangel.themes.di

import com.onelittleangel.cache.di.cacheModule
import com.onelittleangel.data.di.dataModule
import com.onelittleangel.data.repository.DefaultBookmarkRepository
import com.onelittleangel.datastore.di.datastoreModule
import com.onelittleangel.domain.di.domainModule
import com.onelittleangel.domain.theme.GetThemeByNameUseCase
import com.onelittleangel.themes.ThemeViewModel
import com.onelittleangel.themes.ThemesViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val themesModule = module {
    includes(domainModule, dataModule, datastoreModule, cacheModule)

    factory { (name: String) ->
        ThemeViewModel(
            name = name,
            getThemeByNameUseCase = GetThemeByNameUseCase(get(), get(), get()),
            bookmarkRepository = DefaultBookmarkRepository(get(), get())
        )
    }

    singleOf(::ThemesViewModel)
}