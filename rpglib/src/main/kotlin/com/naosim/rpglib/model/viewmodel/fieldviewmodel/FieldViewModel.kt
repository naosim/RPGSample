package com.naosim.rpglib.model.viewmodel.fieldviewmodel

import com.naosim.rpglib.model.value.field.*

interface FieldViewModel {
    val onload: (FieldViewModel) -> Unit
    val onstep: (FieldViewModel, PositionAndDirection) -> Unit
    fun getPositionAndDirection(callback: (PositionAndDirection) -> Unit)
    fun gotoPosition(pos: Position)
    fun updateFieldLayer(fieldLayer: FieldLayer)
    fun runFieldEffect(FieldEffect: FieldEffect, callback: () -> Unit)
    fun onButtonDown(arrowButtonType: ArrowButtonType)
    fun onButtonUp(arrowButtonType: ArrowButtonType)

    fun updateField(field: Field) {
        updateFieldLayer(field.backFieldLayer)
        field.frontFieldLayer?.let { updateFieldLayer(it) }
    }

    fun updateFieldAndGo(field: Field, x: X, y: Y) {
        updateField(field)
        gotoPosition(Position(field.fieldName, x, y))
    }
}

interface FieldViewModelWithCall<I>: FieldViewModel, Caller<I>

interface Caller<I> {
    fun call(input: I, callback: (I) -> Unit = {})
}
