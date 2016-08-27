package com.naosim.rpglib.model.value.field

import com.naosim.rpglib.model.value.ValueImutable

interface FieldLayerName : ValueImutable<String> {
    override val value: String
}

enum class FieldLayerNameType(): FieldLayerName {
    back,
    front;
    override val value = name

}