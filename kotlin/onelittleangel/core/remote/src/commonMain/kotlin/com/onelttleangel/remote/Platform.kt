package com.onelttleangel.remote

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform