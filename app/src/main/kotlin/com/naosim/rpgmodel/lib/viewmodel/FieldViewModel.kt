package com.naosim.rpgmodel.lib.viewmodel

import com.naosim.rpgmodel.lib.value.field.ArrowButtonType
import com.naosim.rpgmodel.lib.value.field.FieldEffect
import com.naosim.rpgmodel.lib.value.field.FieldLayer
import com.naosim.rpgmodel.lib.value.field.Position

interface FieldViewModel {
    fun getPosition(callback: (Position) -> Unit)
    fun gotoPosition(pos: Position)
    fun updateFieldLayer(fieldLayer: FieldLayer)
    fun runFieldEffect(FieldEffect: FieldEffect, callback: () -> Unit)
    fun onButtonDown(arrowButtonType: ArrowButtonType)
    fun onButtonUp(arrowButtonType: ArrowButtonType)
}