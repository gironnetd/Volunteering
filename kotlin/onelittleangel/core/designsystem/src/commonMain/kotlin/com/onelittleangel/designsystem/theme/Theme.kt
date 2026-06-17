package com.onelittleangel.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.onelittleangel.designsystem.theme.PaletteTokens.LocalSystemInDarkTheme
import com.onelittleangel.model.ThemeBrand

/**
 * Light default theme color scheme
 */
//@VisibleForTesting
@Composable
fun LightPrimaryColorScheme() = lightColorScheme(
    primary = PaletteTokens.Primary40,
    onPrimary = Color.White,
    primaryContainer = PaletteTokens.Primary90,
    onPrimaryContainer = PaletteTokens.Primary10,
    /*secondary = PaletteTokens.Secondary40,
    onSecondary = Color.White,
    secondaryContainer = PaletteTokens.Secondary90,
    onSecondaryContainer = PaletteTokens.Secondary10,
    tertiary = PaletteTokens.Tertiary40,
    onTertiary = Color.White,
    tertiaryContainer = PaletteTokens.Tertiary90,
    onTertiaryContainer = PaletteTokens.Tertiary10,*/
    error = PaletteTokens.Error40,
    onError = Color.White,
    errorContainer = PaletteTokens.Error90,
    onErrorContainer = PaletteTokens.Error10,
    background = PaletteTokens.White,
    /*onBackground = PaletteTokens.OnBackground(),
    surface = PaletteTokens.Surface(),
    onSurface = PaletteTokens.OnSurface(),
    surfaceVariant = PaletteTokens.SurfaceVariant(),
    onSurfaceVariant = PaletteTokens.OnSurfaceVariant(),
    inverseSurface = PaletteTokens.InverseSurface(),
    inverseOnSurface = PaletteTokens.InverseOnSurface(),
    outline = PaletteTokens.Outline(),*/
)

/**
 * Dark default theme color scheme
 */
//@VisibleForTesting
@Composable
fun DarkPrimaryColorScheme() = darkColorScheme(
    primary = PaletteTokens.Primary80,
    onPrimary = PaletteTokens.Primary5,
    primaryContainer = PaletteTokens.Primary30,
    onPrimaryContainer = PaletteTokens.Primary90,
    /*secondary = PaletteTokens.Secondary80,
    onSecondary = PaletteTokens.Secondary20,
    secondaryContainer = PaletteTokens.Secondary30,
    onSecondaryContainer = PaletteTokens.Secondary90,
    tertiary = PaletteTokens.Tertiary80,
    onTertiary = PaletteTokens.Tertiary20,
    tertiaryContainer = PaletteTokens.Tertiary30,
    onTertiaryContainer = PaletteTokens.Tertiary90,*/
    error = PaletteTokens.Error80,
    onError = PaletteTokens.Error20,
    errorContainer = PaletteTokens.Error30,
    onErrorContainer = PaletteTokens.Error90,
    background = PaletteTokens.DarkPurpleGray10,
    onBackground = PaletteTokens.DarkPurpleGray90,
    surface = PaletteTokens.DarkPurpleGray10,
    onSurface = PaletteTokens.DarkPurpleGray90,
    surfaceVariant = PaletteTokens.PurpleGray30,
    onSurfaceVariant = PaletteTokens.PurpleGray80,
    inverseSurface = PaletteTokens.DarkPurpleGray90,
    inverseOnSurface = PaletteTokens.DarkPurpleGray10,
    outline = PaletteTokens.PurpleGray60,
)

@Composable
fun LightSecondaryColorScheme() = lightColorScheme(
    primary = PaletteTokens.Secondary40,
    onPrimary = Color.White,
    primaryContainer = PaletteTokens.Secondary90,
    onPrimaryContainer = PaletteTokens.Secondary10,
    /*secondary = PaletteTokens.Secondary40,
    onSecondary = Color.White,
    secondaryContainer = PaletteTokens.Secondary90,
    onSecondaryContainer = PaletteTokens.Secondary10,
    tertiary = PaletteTokens.Tertiary40,
    onTertiary = Color.White,
    tertiaryContainer = PaletteTokens.Tertiary90,
    onTertiaryContainer = PaletteTokens.Tertiary10,*/
    error = PaletteTokens.Error40,
    onError = Color.White,
    errorContainer = PaletteTokens.Error90,
    onErrorContainer = PaletteTokens.Error10,
    background = PaletteTokens.White,
    /*onBackground = PaletteTokens.OnBackground(),
    surface = PaletteTokens.Surface(),
    onSurface = PaletteTokens.OnSurface(),
    surfaceVariant = PaletteTokens.SurfaceVariant(),
    onSurfaceVariant = PaletteTokens.OnSurfaceVariant(),
    inverseSurface = PaletteTokens.InverseSurface(),
    inverseOnSurface = PaletteTokens.InverseOnSurface(),
    outline = PaletteTokens.Outline(),*/
)

/**
 * Dark default theme color scheme
 */
//@VisibleForTesting
@Composable
fun DarkSecondaryColorScheme() = darkColorScheme(
    primary = PaletteTokens.Secondary80,
    onPrimary = PaletteTokens.Secondary5,
    primaryContainer = PaletteTokens.Secondary30,
    onPrimaryContainer = PaletteTokens.Secondary90,
    /*secondary = PaletteTokens.Secondary80,
    onSecondary = PaletteTokens.Secondary20,
    secondaryContainer = PaletteTokens.Secondary30,
    onSecondaryContainer = PaletteTokens.Secondary90,
    tertiary = PaletteTokens.Tertiary80,
    onTertiary = PaletteTokens.Tertiary20,
    tertiaryContainer = PaletteTokens.Tertiary30,
    onTertiaryContainer = PaletteTokens.Tertiary90,*/
    error = PaletteTokens.Error80,
    onError = PaletteTokens.Error20,
    errorContainer = PaletteTokens.Error30,
    onErrorContainer = PaletteTokens.Error90,
    background = PaletteTokens.DarkPurpleGray10,
    onBackground = PaletteTokens.DarkPurpleGray90,
    surface = PaletteTokens.DarkPurpleGray10,
    onSurface = PaletteTokens.DarkPurpleGray90,
    surfaceVariant = PaletteTokens.PurpleGray30,
    onSurfaceVariant = PaletteTokens.PurpleGray80,
    inverseSurface = PaletteTokens.DarkPurpleGray90,
    inverseOnSurface = PaletteTokens.DarkPurpleGray10,
    outline = PaletteTokens.PurpleGray60,
)

@Composable
fun LightTertiaryColorScheme() = lightColorScheme(
    primary = PaletteTokens.Tertiary40,
    onPrimary = Color.White,
    primaryContainer = PaletteTokens.Tertiary90,
    onPrimaryContainer = PaletteTokens.Tertiary10,
    /*secondary = PaletteTokens.Secondary40,
    onSecondary = Color.White,
    secondaryContainer = PaletteTokens.Secondary90,
    onSecondaryContainer = PaletteTokens.Secondary10,
    tertiary = PaletteTokens.Tertiary40,
    onTertiary = Color.White,
    tertiaryContainer = PaletteTokens.Tertiary90,
    onTertiaryContainer = PaletteTokens.Tertiary10,*/
    error = PaletteTokens.Error40,
    onError = Color.White,
    errorContainer = PaletteTokens.Error90,
    onErrorContainer = PaletteTokens.Error10,
    background = PaletteTokens.White,
   /* onBackground = PaletteTokens.OnBackground(),
    surface = PaletteTokens.Surface(),
    onSurface = PaletteTokens.OnSurface(),
    surfaceVariant = PaletteTokens.SurfaceVariant(),
    onSurfaceVariant = PaletteTokens.OnSurfaceVariant(),
    inverseSurface = PaletteTokens.InverseSurface(),
    inverseOnSurface = PaletteTokens.InverseOnSurface(),
    outline = PaletteTokens.Outline(),*/
)

/**
 * Dark default theme color scheme
 */
//@VisibleForTesting
@Composable
fun DarkTertiaryColorScheme() = darkColorScheme(
    primary = PaletteTokens.Tertiary80,
    onPrimary = PaletteTokens.Tertiary5,
    primaryContainer = PaletteTokens.Tertiary30,
    onPrimaryContainer = PaletteTokens.Tertiary90,
    /*secondary = PaletteTokens.Secondary80,
    onSecondary = PaletteTokens.Secondary20,
    secondaryContainer = PaletteTokens.Secondary30,
    onSecondaryContainer = PaletteTokens.Secondary90,
    tertiary = PaletteTokens.Tertiary80,
    onTertiary = PaletteTokens.Tertiary20,
    tertiaryContainer = PaletteTokens.Tertiary30,
    onTertiaryContainer = PaletteTokens.Tertiary90,*/
    error = PaletteTokens.Error80,
    onError = PaletteTokens.Error20,
    errorContainer = PaletteTokens.Error30,
    onErrorContainer = PaletteTokens.Error90,
    background = PaletteTokens.DarkPurpleGray10,
    onBackground = PaletteTokens.DarkPurpleGray90,
    surface = PaletteTokens.DarkPurpleGray10,
    onSurface = PaletteTokens.DarkPurpleGray90,
    surfaceVariant = PaletteTokens.PurpleGray30,
    onSurfaceVariant = PaletteTokens.PurpleGray80,
    inverseSurface = PaletteTokens.DarkPurpleGray90,
    inverseOnSurface = PaletteTokens.DarkPurpleGray10,
    outline = PaletteTokens.PurpleGray60,
)

@Composable
fun LightQuaternaryColorScheme() = lightColorScheme(
    primary = PaletteTokens.Quaternary40,
    onPrimary = Color.White,
    primaryContainer = PaletteTokens.Quaternary90,
    onPrimaryContainer = PaletteTokens.Quaternary10,
    /*secondary = PaletteTokens.Secondary40,
    onSecondary = Color.White,
    secondaryContainer = PaletteTokens.Secondary90,
    onSecondaryContainer = PaletteTokens.Secondary10,
    tertiary = PaletteTokens.Tertiary40,
    onTertiary = Color.White,
    tertiaryContainer = PaletteTokens.Tertiary90,
    onTertiaryContainer = PaletteTokens.Tertiary10,*/
    error = PaletteTokens.Error40,
    onError = Color.White,
    errorContainer = PaletteTokens.Error90,
    onErrorContainer = PaletteTokens.Error10,
    background = PaletteTokens.White,
    /*onBackground = PaletteTokens.OnBackground(),
    surface = PaletteTokens.Surface(),
    onSurface = PaletteTokens.OnSurface(),
    surfaceVariant = PaletteTokens.SurfaceVariant(),
    onSurfaceVariant = PaletteTokens.OnSurfaceVariant(),
    inverseSurface = PaletteTokens.InverseSurface(),
    inverseOnSurface = PaletteTokens.InverseOnSurface(),
    outline = PaletteTokens.Outline(),*/
)

/**
 * Dark default theme color scheme
 */
//@VisibleForTesting
@Composable
fun DarkQuaternaryColorScheme() = darkColorScheme(
    primary = PaletteTokens.Quaternary80,
    onPrimary = PaletteTokens.Quaternary5,
    primaryContainer = PaletteTokens.Quaternary30,
    onPrimaryContainer = PaletteTokens.Quaternary90,
    /*secondary = PaletteTokens.Secondary80,
    onSecondary = PaletteTokens.Secondary20,
    secondaryContainer = PaletteTokens.Secondary30,
    onSecondaryContainer = PaletteTokens.Secondary90,
    tertiary = PaletteTokens.Tertiary80,
    onTertiary = PaletteTokens.Tertiary20,
    tertiaryContainer = PaletteTokens.Tertiary30,
    onTertiaryContainer = PaletteTokens.Tertiary90,*/
    error = PaletteTokens.Error80,
    onError = PaletteTokens.Error20,
    errorContainer = PaletteTokens.Error30,
    onErrorContainer = PaletteTokens.Error90,
    background = PaletteTokens.DarkPurpleGray10,
    onBackground = PaletteTokens.DarkPurpleGray90,
    surface = PaletteTokens.DarkPurpleGray10,
    onSurface = PaletteTokens.DarkPurpleGray90,
    surfaceVariant = PaletteTokens.PurpleGray30,
    onSurfaceVariant = PaletteTokens.PurpleGray80,
    inverseSurface = PaletteTokens.DarkPurpleGray90,
    inverseOnSurface = PaletteTokens.DarkPurpleGray10,
    outline = PaletteTokens.PurpleGray60,
)

/**
 * Light Android gradient colors
 */
@Composable
fun LightPrimaryGradientColors() = GradientColors(container = PaletteTokens.Primary95.copy(alpha = 0.8f))

/**
 * Dark Android gradient colors
 */
@Composable
fun DarkPrimaryGradientColors() = GradientColors(container = Color.Black)

/**
 * Light Android gradient colors
 */
@Composable
fun LightSecondaryGradientColors() = GradientColors(container = PaletteTokens.Secondary95.copy(alpha = 0.8f))

/**
 * Dark Android gradient colors
 */
@Composable
fun DarkSecondaryGradientColors() = GradientColors(container = Color.Black)

/**
 * Light Android gradient colors
 */
@Composable
fun LightTertiaryGradientColors() = GradientColors(container = PaletteTokens.Tertiary95.copy(alpha = 0.8f))

/**
 * Dark Android gradient colors
 */
@Composable
fun DarkTertiaryGradientColors() = GradientColors(container = Color.Black)

/**
 * Light Android gradient colors
 */
@Composable
fun LightQuaternaryGradientColors() = GradientColors(container = PaletteTokens.Quaternary95.copy(alpha = 0.8f))

/**
 * Dark Android gradient colors
 */
@Composable
fun DarkQuaternaryGradientColors() = GradientColors(container = Color.Black)

/**
 * Light Android background theme
 */
@Composable
fun LightPrimaryBackgroundTheme() = BackgroundTheme(color = PaletteTokens.Primary95.copy(alpha = 0.3f))

/**
 * Dark Android background theme
 */
@Composable
fun DarkPrimaryBackgroundTheme() = BackgroundTheme(color = Color.Black)

/**
 * Light Android background theme
 */
@Composable
fun LightSecondaryBackgroundTheme() = BackgroundTheme(color = PaletteTokens.Secondary95.copy(alpha = 0.3f))

/**
 * Dark Android background theme
 */
@Composable
fun DarkSecondaryBackgroundTheme() = BackgroundTheme(color = Color.Black)

/**
 * Light Android background theme
 */
@Composable
fun LightTertiaryBackgroundTheme() = BackgroundTheme(color = PaletteTokens.Tertiary95.copy(alpha = 0.3f))

/**
 * Dark Android background theme
 */
@Composable
fun DarkTertiaryBackgroundTheme() = BackgroundTheme(color = Color.Black)

/**
 * Light Android background theme
 */
@Composable
fun LightQuaternaryBackgroundTheme() = BackgroundTheme(color = PaletteTokens.Quaternary95.copy(alpha = 0.3f))

/**
 * Dark Android background theme
 */
@Composable
fun DarkQuaternaryBackgroundTheme() = BackgroundTheme(color = Color.Black)

/**
 * Now in Android theme.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 * @param androidTheme Whether the theme should use the Android theme color scheme instead of the
 *        default theme.
 * @param disableDynamicTheming If `true`, disables the use of dynamic theming, even when it is
 *        supported. This parameter has no effect if [androidTheme] is `true`.
 */

@Composable
fun OlaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeBrand: ThemeBrand = ThemeBrand.primary,
    // disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    // Color scheme
    val colorScheme = when(themeBrand) {
        ThemeBrand.primary -> {
            if (darkTheme) DarkPrimaryColorScheme() else LightPrimaryColorScheme()
        }
        ThemeBrand.secondary -> {
            if (darkTheme) DarkSecondaryColorScheme() else LightSecondaryColorScheme()
        }
        ThemeBrand.tertiary -> {
            if (darkTheme) DarkTertiaryColorScheme() else LightTertiaryColorScheme()
        }
        ThemeBrand.quaternary -> {
            if (darkTheme) DarkQuaternaryColorScheme() else LightQuaternaryColorScheme()
        }
    }

    val gradientColors = when(themeBrand) {
        ThemeBrand.primary -> {
            if (darkTheme) DarkPrimaryGradientColors() else LightPrimaryGradientColors()
        }
        ThemeBrand.secondary -> {
            if (darkTheme) DarkSecondaryGradientColors() else LightSecondaryGradientColors()
        }
        ThemeBrand.tertiary -> {
            if (darkTheme) DarkTertiaryGradientColors() else LightTertiaryGradientColors()
        }
        ThemeBrand.quaternary -> {
            if (darkTheme) DarkQuaternaryGradientColors() else LightQuaternaryGradientColors()
        }
    }

    val tonalPalette: TonalPalette = when(themeBrand) {
        ThemeBrand.primary -> {
            TonalPalette()
        }
        ThemeBrand.secondary -> {
            TonalPalette(
                tonal0 = PaletteTokens.Secondary0,
                tonal5 = PaletteTokens.Secondary5,
                tonal10 = PaletteTokens.Secondary10,
                tonal20 = PaletteTokens.Secondary20,
                tonal30 = PaletteTokens.Secondary30,
                tonal40 = PaletteTokens.Secondary40,
                tonal50 = PaletteTokens.Secondary50,
                tonal60 = PaletteTokens.Secondary60,
                tonal70 = PaletteTokens.Secondary70,
                tonal80 = PaletteTokens.Secondary80,
                tonal90 = PaletteTokens.Secondary90,
                tonal95 = PaletteTokens.Secondary95,
                tonal99 = PaletteTokens.Secondary99,
                tonal100 = PaletteTokens.Secondary100,
            )
        }
        ThemeBrand.tertiary -> {
            TonalPalette(
                tonal0 = PaletteTokens.Tertiary0,
                tonal5 = PaletteTokens.Tertiary5,
                tonal10 = PaletteTokens.Tertiary10,
                tonal20 = PaletteTokens.Tertiary20,
                tonal30 = PaletteTokens.Tertiary30,
                tonal40 = PaletteTokens.Tertiary40,
                tonal50 = PaletteTokens.Tertiary50,
                tonal60 = PaletteTokens.Tertiary60,
                tonal70 = PaletteTokens.Tertiary70,
                tonal80 = PaletteTokens.Tertiary80,
                tonal90 = PaletteTokens.Tertiary90,
                tonal95 = PaletteTokens.Tertiary95,
                tonal99 = PaletteTokens.Tertiary99,
                tonal100 = PaletteTokens.Tertiary100,
            )
        }
        ThemeBrand.quaternary -> {
            TonalPalette(
                tonal0 = PaletteTokens.Quaternary0,
                tonal5 = PaletteTokens.Quaternary5,
                tonal10 = PaletteTokens.Quaternary10,
                tonal20 = PaletteTokens.Quaternary20,
                tonal30 = PaletteTokens.Quaternary30,
                tonal40 = PaletteTokens.Quaternary40,
                tonal50 = PaletteTokens.Quaternary50,
                tonal60 = PaletteTokens.Quaternary60,
                tonal70 = PaletteTokens.Quaternary70,
                tonal80 = PaletteTokens.Quaternary80,
                tonal90 = PaletteTokens.Quaternary90,
                tonal95 = PaletteTokens.Quaternary95,
                tonal99 = PaletteTokens.Quaternary99,
                tonal100 = PaletteTokens.Quaternary100,
            )
        }
    }

    // Background theme
    val backgroundTheme = when(themeBrand) {
        ThemeBrand.primary -> {
            if (darkTheme) DarkPrimaryBackgroundTheme() else LightPrimaryBackgroundTheme()
        }
        ThemeBrand.secondary -> {
            if (darkTheme) DarkSecondaryBackgroundTheme() else LightSecondaryBackgroundTheme()
        }
        ThemeBrand.tertiary -> {
            if (darkTheme) DarkTertiaryBackgroundTheme() else LightTertiaryBackgroundTheme()
        }
        ThemeBrand.quaternary -> {
            if (darkTheme) DarkQuaternaryBackgroundTheme() else LightQuaternaryBackgroundTheme()
        }
    }

    val tintTheme = TintTheme()

    // Composition locals
    CompositionLocalProvider(
        LocalGradientColors provides gradientColors,
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides tintTheme,
        LocalSystemInDarkTheme provides darkTheme,
        LocalTonalPalette provides tonalPalette
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = NiaTypography(),
            content = content,
        )
    }
}
