package com.onelittleangel.foryou.navigation

import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator

const val forYouRoute = "/forYou_route"

fun Navigator.navigateToForYou(navOptions: NavOptions? = null) {
    this.navigate(forYouRoute, navOptions)
}