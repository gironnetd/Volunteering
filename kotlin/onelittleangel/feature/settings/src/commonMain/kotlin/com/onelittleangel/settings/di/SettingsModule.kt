package com.onelittleangel.settings.di

import com.onelittleangel.data.di.dataModule
import com.onelittleangel.domain.di.domainModule
import com.onelittleangel.settings.SettingsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsModule = module {
    includes(domainModule, dataModule)
    singleOf(::SettingsViewModel)
}
