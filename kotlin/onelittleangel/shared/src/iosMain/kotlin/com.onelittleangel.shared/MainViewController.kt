package com.onelittleangel.shared

import androidx.compose.ui.window.ComposeUIViewController
import com.onelittleangel.shared.di.olaAppModule
import com.onelittleangel.shared.ui.OlaApp
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    OlaApp()
}

fun initKoin() {
    startKoin {
        modules(olaAppModule)
    }
}
