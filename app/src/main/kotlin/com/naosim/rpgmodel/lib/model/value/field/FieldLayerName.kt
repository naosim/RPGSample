package com.naosim.rpgmodel.lib.model.value.field

import com.naosim.rpgmodel.lib.model.value.ValueImutable

interface FieldLayerName : ValueImutable<String> {
    override val value: String
}

enum class FieldLayerNameType(): FieldLayerName {
    back,
    front;
    override val value = name

}