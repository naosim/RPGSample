package com.naosim.rpglib.model.viewmodel

import com.naosim.rpglib.model.value.field.PositionAndDirection

interface FieldViewModelFactory {
    fun create(onload: (FieldViewModel) -> Unit, onstep: (FieldViewModel, PositionAndDirection) -> Unit): FieldViewModel
}