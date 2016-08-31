package com.naosim.rpglib.android.fieldviewmodel

interface ModelConverter<A, B> {
    fun encode(a: A): B
    fun decode(b: B): A
}
