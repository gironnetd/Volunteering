package com.onelittleangel.shared.di

import com.onelittleangel.bookmarks.di.bookmarksModule
import com.onelittleangel.explore.di.exploreModule
import com.onelittleangel.recents.di.recentsModule
import com.onelittleangel.settings.di.settingsModule
import com.onelittleangel.shared.MainActivityViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val olaAppModule = module {
    includes(exploreModule, recentsModule, bookmarksModule, settingsModule)
    singleOf(::MainActivityViewModel)
}