package com.onelittleangel.authentication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform