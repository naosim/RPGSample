package com.naosim.rpgmodel.lib.viewmodel

import com.naosim.rpgmodel.lib.value.field.PositionAndDirection

interface FieldViewModelFactory {
    fun create(onload: (FieldViewModel) -> Unit, onstep: (FieldViewModel, PositionAndDirection) -> Unit): FieldViewModel
}