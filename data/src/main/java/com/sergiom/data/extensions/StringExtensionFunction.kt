package com.sergiom.data.extensions

fun String?.getNextPage(): Int? {
    val queries = this?.split("?")
    val pages = queries?.last()?.split("&")
    val num = pages?.first()?.split("=")
    return num?.last()?.toInt()
}