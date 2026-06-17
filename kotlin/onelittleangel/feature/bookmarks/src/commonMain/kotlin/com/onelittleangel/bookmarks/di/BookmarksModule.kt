package com.onelittleangel.bookmarks.di

import com.onelittleangel.bookmarks.BookmarkGroupsViewModel
import com.onelittleangel.bookmarks.BookmarksViewModel
import com.onelittleangel.data.di.dataModule
import com.onelittleangel.domain.di.domainModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val bookmarksModule = module {
    includes(domainModule, dataModule/*, datastoreModule*/)

    factory { (groupId: String) ->
        BookmarksViewModel(
            groupId = groupId,
            get(), get()
        )
    }

    singleOf(::BookmarkGroupsViewModel)
}