package com.naosim.rpgmodel.lib.model.viewmodel

import com.naosim.rpgmodel.lib.model.value.field.PositionAndDirection

interface FieldViewModelFactory {
    fun create(onload: (FieldViewModel) -> Unit, onstep: (FieldViewModel, PositionAndDirection) -> Unit): FieldViewModel
}