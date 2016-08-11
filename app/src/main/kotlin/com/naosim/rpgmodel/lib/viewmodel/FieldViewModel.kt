package com.naosim.rpgmodel.lib.viewmodel

import com.naosim.rpgmodel.lib.value.ValueImutable

interface FieldViewModel {
    fun getPosition(callback: (Position) -> Unit)
    fun gotoPosition(pos: Position)
    fun updateFieldLayer(fieldLayer: FieldLayer)
    fun runFieldEffect(FieldEffect: FieldEffect, callback: () -> Unit)
    fun onButtonDown(arrowButtonType: ArrowButtonType)
    fun onButtonUp(arrowButtonType: ArrowButtonType)
}

class FieldName(override val value: String) : ValueImutable<String>
class X(override val value: Int) : ValueImutable<Int>
class Y(override val value: Int) : ValueImutable<Int>
class Position(val fieldName: FieldName, val x: X, val y: Y)

class FieldLayerName(override val value: String) : ValueImutable<String>
class FieldData(override val value: List<List<Int>>) : ValueImutable<List<List<Int>>>
class FieldLayer(val fieldName: FieldName, val fieldLayerName: FieldLayerName, val fieldData: FieldData)

class FieldEffect(fieldEffectType: FieldEffectType, fieldEffectDuration: FieldEffectDuration)
enum class FieldEffectType {
    viberate
}
class FieldEffectDuration(override val value: Long) : ValueImutable<Long>

enum class ArrowButtonType {
    up, down, left, right
}