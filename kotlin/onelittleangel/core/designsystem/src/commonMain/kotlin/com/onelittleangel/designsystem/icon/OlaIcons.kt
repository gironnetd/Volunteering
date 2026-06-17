package com.onelittleangel.designsystem.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import com.onelittleangel.designsystem.Resources
import com.onelittleangel.designsystem.theme.LocalTonalPalette
import dev.icerock.moko.resources.compose.painterResource

object OlaIcons {
    @Composable
    fun Dove()  = Image(painter = painterResource(Resources.images.dove_icon), contentDescription = null)

    @Composable
    fun PrimaryFlowersLeft() = Image(painter = painterResource(Resources.images.primary_flowers_left), contentDescription = null)

    @Composable
    fun PrimaryFlowersRight() = Image(painter = painterResource(Resources.images.primary_flowers_right), contentDescription = null)

    @Composable
    fun DarkPrimaryFlowersLeft() = Image(painter = painterResource(Resources.images.dark_primary_flowers_left), contentDescription = null)

    @Composable
    fun DarkPrimaryFlowersRight() = Image(painter = painterResource(Resources.images.dark_primary_flowers_right), contentDescription = null)

    @Composable
    fun SecondaryFlowersLeft() = Image(painter = painterResource(Resources.images.secondary_flowers_left), contentDescription = null)

    @Composable
    fun SecondaryFlowersRight() = Image(painter = painterResource(Resources.images.secondary_flowers_right), contentDescription = null)

    @Composable
    fun DarkSecondaryFlowersLeft() = Image(painter = painterResource(Resources.images.dark_secondary_flowers_left), contentDescription = null)

    @Composable
    fun DarkSecondaryFlowersRight() = Image(painter = painterResource(Resources.images.dark_secondary_flowers_right), contentDescription = null)

    @Composable
    fun TertiaryFlowersLeft() = Image(painter = painterResource(Resources.images.tertiary_flowers_left), contentDescription = null)

    @Composable
    fun TertiaryFlowersRight() = Image(painter = painterResource(Resources.images.tertiary_flowers_right), contentDescription = null)

    @Composable
    fun DarkTertiaryFlowersLeft() = Image(painter = painterResource(Resources.images.dark_tertiary_flowers_left), contentDescription = null)

    @Composable
    fun DarkTertiaryFlowersRight() = Image(painter = painterResource(Resources.images.dark_tertiary_flowers_right), contentDescription = null)

    @Composable
    fun QuaternaryFlowersLeft() = Image(painter = painterResource(Resources.images.quaternary_flowers_left), contentDescription = null)

    @Composable
    fun QuaternaryFlowersRight() = Image(painter = painterResource(Resources.images.quaternary_flowers_right), contentDescription = null)

    @Composable
    fun DarkQuaternaryFlowersLeft() = Image(painter = painterResource(Resources.images.dark_quaternary_flowers_left), contentDescription = null)

    @Composable
    fun DarkQuaternaryFlowersRight() = Image(painter = painterResource(Resources.images.dark_quaternary_flowers_right), contentDescription = null)

    @Composable
    fun BookmarkBorder() = Image(
        painter = painterResource(Resources.images.bookmark_border), contentDescription = null,
        colorFilter = ColorFilter.tint(LocalTonalPalette.current.tonal60)
    )

    @Composable
    fun Bookmark() = Image(
        painter = painterResource(Resources.images.bookmark), contentDescription = null,
        colorFilter = ColorFilter.tint(LocalTonalPalette.current.tonal60)
    )
}