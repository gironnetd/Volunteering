package com.onelittleangel.foryou.di

import com.onelittleangel.data.di.dataModule
import com.onelittleangel.domain.di.domainModule
import com.onelittleangel.foryou.ForYouViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val forYouModule = module {
    includes(domainModule, dataModule/*, datastoreModule*/)
    singleOf(::ForYouViewModel)
}