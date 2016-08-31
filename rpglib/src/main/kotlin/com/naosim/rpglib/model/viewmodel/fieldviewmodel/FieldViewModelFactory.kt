package com.naosim.rpglib.model.viewmodel.fieldviewmodel

import com.naosim.rpglib.model.value.field.PositionAndDirection

interface FieldViewModelFactory<F: FieldViewModel> {
    fun create(
            onload: (F) -> Unit,
            onstep: (F, PositionAndDirection) -> Unit
    ): F
}