package com.naosim.rpgmodel.lib.model.value.field

import com.naosim.rpgmodel.lib.model.value.ValueImutable

interface FieldName : ValueImutable<String> {
    override val value: String
}
class FieldNameImpl(override val value: String) : FieldName