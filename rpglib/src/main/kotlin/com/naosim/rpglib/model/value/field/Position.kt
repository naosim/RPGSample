package com.naosim.rpglib.model.value.field


open class Position(val fieldName: FieldName, val x: X, val y: Y) {
    fun isSame(fieldName: FieldName, x: X, y: Y): Boolean {
        return isSame(Position(fieldName, x, y))
    }

    fun isSame(fieldName: FieldName, x: Int, y: Int): Boolean {
        return isSame(fieldName, X(x), Y(y))
    }

    fun isSame(otherPosition: Position): Boolean {
        return this.fieldName.value == otherPosition.fieldName.value
                && this.x.value == otherPosition.x.value
                && this.y.value == otherPosition.y.value
    }
}