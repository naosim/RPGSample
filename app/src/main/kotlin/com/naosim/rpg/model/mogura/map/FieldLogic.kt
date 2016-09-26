package com.naosim.rpg.model.mogura.map

import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.field.Field
import com.naosim.rpglib.model.value.field.FieldName
import com.naosim.rpglib.model.value.field.Position
import com.naosim.rpglib.model.value.field.PositionAndDirection
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldAndPosition

interface FieldLogic<I: Item> {
    fun onUpdatePositionAndDirection(positionAndDirection: PositionAndDirection): Boolean
    fun check(positionAndDirection: PositionAndDirection)
    fun useItem(positionAndDirection: PositionAndDirection, item: I): Boolean {
        return false
    }
}

interface FieldAndFieldLogic<I: Item>: FieldLogic<I> {
    val field: Field
}

interface HasPosition {
    val position: Position
}

interface HasFieldName {
    val fieldName: FieldName
}

interface HasFieldNameAndHasPosition: HasPosition, HasFieldName {
    override val fieldName: FieldName
    override val position: Position
}

interface GetFieldAndPosition<T: HasPosition> {
    fun getFieldAndPosition(t: T): FieldAndPosition
}