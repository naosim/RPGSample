package com.naosim.rpgmodel.lib.viewmodel

import com.naosim.rpgmodel.lib.value.field.*

interface FieldViewModel {
    val onload: (FieldViewModel) -> Unit
    val onstep: (FieldViewModel, Position) -> Unit
    fun getPosition(callback: (Position) -> Unit)
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

interface FieldViewModelFactory {
    fun create(onload: (FieldViewModel) -> Unit, onstep: (FieldViewModel, Position) -> Unit): FieldViewModel
}