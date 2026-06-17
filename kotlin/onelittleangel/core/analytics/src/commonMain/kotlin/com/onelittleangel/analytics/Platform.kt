package com.onelittleangel.analytics

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform