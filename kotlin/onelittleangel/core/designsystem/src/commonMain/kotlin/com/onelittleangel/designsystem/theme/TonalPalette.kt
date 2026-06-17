package com.onelittleangel.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class TonalPalette(
    val tonal0: Color = PaletteTokens.Primary0,
    val tonal5: Color = PaletteTokens.Primary5,
    val tonal10: Color = PaletteTokens.Primary10,
    val tonal20: Color = PaletteTokens.Primary20,
    val tonal30: Color = PaletteTokens.Primary30,
    val tonal40: Color = PaletteTokens.Primary40,
    val tonal50: Color  = PaletteTokens.Primary50,
    val tonal60: Color = PaletteTokens.Primary60,
    val tonal70: Color = PaletteTokens.Primary70,
    val tonal80: Color = PaletteTokens.Primary80,
    val tonal90: Color = PaletteTokens.Primary90,
    val tonal95: Color = PaletteTokens.Primary95,
    val tonal99: Color = PaletteTokens.Primary99,
    val tonal100: Color = PaletteTokens.Primary100
)

/**
 * A composition local for [BackgroundTheme].
 */
val LocalTonalPalette = staticCompositionLocalOf { TonalPalette() }