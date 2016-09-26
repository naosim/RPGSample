package com.naosim.rpg.model.mogura.map

import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.map.b1.MoguraB1FieldFactory
import com.naosim.rpg.model.mogura.map.b1.MoguraB1FieldLogic
import com.naosim.rpg.model.mogura.map.b2.MoguraB2FieldFactory
import com.naosim.rpg.model.mogura.map.b2.MoguraB2FieldLogic
import com.naosim.rpg.model.mogura.map.b2.MoguraB3FieldFactory
import com.naosim.rpg.model.mogura.map.b2.MoguraB3FieldLogic
import com.naosim.rpg.model.mogura.map.f1.MoguraF1FieldFactory
import com.naosim.rpg.model.mogura.map.f1.MoguraF1FieldLogic
import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.field.Field
import com.naosim.rpglib.model.value.field.FieldName
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldAndPosition

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

class MoguraFieldMap(val globalContainer: MoguraGlobalContainer, eventCallback: (MoguraMapMoveEvent)->Unit) {
    val f1 = MoguraF1FieldLogic(globalContainer, MoguraF1FieldFactory(globalContainer), eventCallback)
    val b1 = MoguraB1FieldLogic(globalContainer, MoguraB1FieldFactory(globalContainer), eventCallback)
    val b2 = MoguraB2FieldLogic(globalContainer, MoguraB2FieldFactory(globalContainer), eventCallback)
    val b3 = MoguraB3FieldLogic(globalContainer, MoguraB3FieldFactory(globalContainer), eventCallback)

    fun getField(fieldName: FieldName): Field {
        return getFieldLogic(fieldName).field
    }

    fun getFieldAndPosition(mapMoveEvent: MoguraMapMoveEvent): FieldAndPosition {
        val field = when(mapMoveEvent.fieldName) {
            MoguraFieldName.f1 -> this.f1.field
            MoguraFieldName.b1 -> this.b1.field
            MoguraFieldName.b2 -> this.b2.field
            MoguraFieldName.b3 -> this.b3.field
            else -> this.f1.field
        }

        return FieldAndPosition(field, mapMoveEvent.position.x, mapMoveEvent.position.y)
    }

    fun getFieldLogic(fieldName: FieldName): FieldAndFieldLogic<Item> {
        return when(getMoguraFieldName(fieldName)) {
            MoguraFieldName.f1 -> this.f1
            MoguraFieldName.b1 -> this.b1
            MoguraFieldName.b2 -> this.b2
            MoguraFieldName.b3 -> this.b3
            else -> this.f1
        }
    }
}

