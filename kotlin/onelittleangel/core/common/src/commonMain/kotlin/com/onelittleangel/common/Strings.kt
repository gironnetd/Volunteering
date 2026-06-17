package com.onelittleangel.common

import doist.x.normalize.Form
import doist.x.normalize.normalize

private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

fun CharSequence.unaccent(): String {
    val temp = this.toString().normalize(Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}