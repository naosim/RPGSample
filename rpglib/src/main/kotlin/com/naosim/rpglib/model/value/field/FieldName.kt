package com.naosim.rpglib.model.value.field

import com.naosim.rpglib.model.value.ValueImutable

interface FieldName : ValueImutable<String> {
    override val value: String
}
class FieldNameImpl(override val value: String) : FieldName