package com.onelittleangel.explore.di

import com.onelittleangel.authors.di.authorsModule
import com.onelittleangel.books.di.booksModule
import com.onelittleangel.faiths.di.faithsModule
import com.onelittleangel.foryou.di.forYouModule
import com.onelittleangel.themes.di.themesModule
import org.koin.dsl.module

val exploreModule = module {
    includes(forYouModule, authorsModule, booksModule, faithsModule, themesModule)
}