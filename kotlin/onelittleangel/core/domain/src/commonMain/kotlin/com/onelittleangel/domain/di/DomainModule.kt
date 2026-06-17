package com.onelittleangel.domain.di

import com.onelittleangel.data.di.dataModule
import com.onelittleangel.domain.author.GetAuthorByIdUseCase
import com.onelittleangel.domain.author.GetAuthorsUseCase
import com.onelittleangel.domain.book.GetBookByIdUseCase
import com.onelittleangel.domain.book.GetBooksUseCase
import com.onelittleangel.domain.bookmark.GetBookmarkGroupByIdUseCase
import com.onelittleangel.domain.bookmark.GetBookmarksUseCase
import com.onelittleangel.domain.century.GetCenturiesUseCase
import com.onelittleangel.domain.faith.GetFaithByIdUseCase
import com.onelittleangel.domain.faith.GetFaithsUseCase
import com.onelittleangel.domain.faith.GetMainFaithsUseCase
import com.onelittleangel.domain.foryou.GetForYouBiographiesUseCase
import com.onelittleangel.domain.foryou.GetForYouPicturesUseCase
import com.onelittleangel.domain.foryou.GetForYouQuotesUseCase
import com.onelittleangel.domain.foryou.GetForYouUseCase
import com.onelittleangel.domain.theme.GetMainThemesUseCase
import com.onelittleangel.domain.theme.GetThemeByIdUseCase
import com.onelittleangel.domain.theme.GetThemesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    includes(dataModule)
    singleOf(::GetAuthorByIdUseCase)
    singleOf(::GetAuthorsUseCase)
    singleOf(::GetBookByIdUseCase)
    singleOf(::GetBooksUseCase)
    singleOf(::GetBookmarksUseCase)
    singleOf(::GetBookmarkGroupByIdUseCase)
    singleOf(::GetCenturiesUseCase)
    singleOf(::GetFaithByIdUseCase)
    singleOf(::GetFaithsUseCase)
    singleOf(::GetMainFaithsUseCase)
    singleOf(::GetForYouBiographiesUseCase)
    singleOf(::GetForYouPicturesUseCase)
    singleOf(::GetForYouQuotesUseCase)
    factory {
        GetForYouUseCase(get(), get(), get(), get())
    }
    singleOf(::GetThemeByIdUseCase)
    singleOf(::GetThemesUseCase)
    singleOf(::GetMainThemesUseCase)
}