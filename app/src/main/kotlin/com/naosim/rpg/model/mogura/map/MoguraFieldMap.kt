package com.naosim.rpg.model.mogura.map

import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.map.b1.MoguraB1FieldFactory
import com.naosim.rpg.model.mogura.map.b1.MoguraB1FieldLogic
import com.naosim.rpg.model.mogura.map.f1.MoguraF1FieldFactory
import com.naosim.rpg.model.mogura.map.f1.MoguraF1FieldLogic
import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.field.Field
import com.naosim.rpglib.model.value.field.FieldName

enum class MoguraFieldName(): FieldName {
    f1,
    b1,
    b2,
    b3,
    b4;
    override val value = name
}
fun getMoguraFieldName(fieldName: FieldName): MoguraFieldName {
    return MoguraFieldName.valueOf(fieldName.value)
}

class MoguraFieldMap(val globalContainer: MoguraGlobalContainer, eventCallback: (MoguraMapEvent)->Unit) {
    val f1 = MoguraF1FieldLogic(globalContainer, MoguraF1FieldFactory(globalContainer), eventCallback)
    val b1 = MoguraB1FieldLogic(globalContainer, MoguraB1FieldFactory(globalContainer), eventCallback)

    fun getField(fieldName: FieldName): Field {
        return getFieldLogic(fieldName).field
    }

    fun getFieldLogic(fieldName: FieldName): FieldAndFieldLogic<Item> {
        return when(getMoguraFieldName(fieldName)) {
            MoguraFieldName.f1 -> this.f1
            MoguraFieldName.b1 -> this.b1
            else -> this.f1
        }
    }
}

