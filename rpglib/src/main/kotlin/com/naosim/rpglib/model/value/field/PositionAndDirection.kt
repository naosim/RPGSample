package com.naosim.rpglib.model.value.field

class PositionAndDirection(val position: Position, val direction: Direction) {
    fun getFrontPosition(): FrontPosition {
        return when(direction) {
            Direction.up -> FrontPosition(position.fieldName, position.x, Y(position.y.value - 1))
            Direction.down -> FrontPosition(position.fieldName, position.x, Y(position.y.value + 1))
            Direction.right -> FrontPosition(position.fieldName, X(position.x.value + 1), position.y)
            else -> FrontPosition(position.fieldName, X(position.x.value - 1), position.y)
        }
    }
}

class FrontPosition(fieldName: FieldName, x: X, y: Y): Position(fieldName, x, y)

