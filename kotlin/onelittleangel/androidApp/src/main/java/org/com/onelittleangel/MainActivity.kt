package org.com.onelittleangel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.onelittleangel.shared.ui.OlaApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val systemUiController = rememberSystemUiController()

            SideEffect {
                // set transparent color so that our image is visible
                // under the status bar
                systemUiController.setStatusBarColor(color = Color.Transparent)

                // not working
                systemUiController.setNavigationBarColor(color = Color.Transparent)
            }

            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
                OlaApp(
                    /*modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                    }*/
                )
            }
        }
    }
}
