package com.naosim.rpgmodel.lib.value.field

enum class Direction {
    down, left, right, up
}
fun createDirection(v: String): Direction {
    return Direction.valueOf(v);
}