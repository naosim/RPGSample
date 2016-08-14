package com.naosim.rpgmodel.lib.value.field

import com.naosim.rpgmodel.lib.value.ValueImutable

interface FieldLayerName : ValueImutable<String> {
    override val value: String
}

enum class FieldLayerNameType(): FieldLayerName {
    back,
    front;
    override val value = name

}