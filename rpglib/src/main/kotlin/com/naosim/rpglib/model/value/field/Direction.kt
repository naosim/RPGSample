package com.naosim.rpglib.model.value.field

enum class Direction {
    down, left, right, up
}
fun createDirection(v: String): Direction {
    return Direction.valueOf(v);
}