package com.naosim.rpglib.model.viewmodel.fieldviewmodel

import com.naosim.rpglib.model.value.field.PositionAndDirection

interface FieldViewModelFactory {
    fun create(
            onload: (FieldViewModel) -> Unit,
            onstep: (FieldViewModel, PositionAndDirection) -> Unit
    ): FieldViewModel
}