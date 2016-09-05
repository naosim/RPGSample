package com.naosim.rpglib.model.value.field

class FieldDataAndFieldCollisionData(
        val fieldData: FieldData,
        val fieldCollisionData: FieldCollisionData? = null
) {
    fun set(x: X, y: Y, fieldValue: FieldValue, collisionValue: Int? = null) {
        fieldData.set(x, y, fieldValue)
        collisionValue?.let { fieldCollisionData!!.set(x, y, it) }
    }
}