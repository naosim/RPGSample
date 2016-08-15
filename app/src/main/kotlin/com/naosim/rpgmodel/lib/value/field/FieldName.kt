package com.naosim.rpgmodel.lib.value.field

import com.naosim.rpgmodel.lib.value.ValueImutable

interface FieldName : ValueImutable<String> {
    override val value: String
}
class FieldNameImpl(override val value: String) : FieldName