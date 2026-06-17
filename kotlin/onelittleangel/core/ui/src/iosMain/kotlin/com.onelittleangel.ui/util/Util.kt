package com.onelittleangel.ui.util

// iOS
import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()
