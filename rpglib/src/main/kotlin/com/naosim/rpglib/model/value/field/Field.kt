package com.naosim.rpglib.model.value.field

import com.naosim.rpglib.model.value.ValueImutable
import com.naosim.rpglib.model.viewmodel.sound.bgm.HasBGM

open class Field(
        open val fieldName: FieldName,
        open val hasBGM: HasBGM? = null,
        private val backFieldDataAndFieldCollisionData: FieldDataAndFieldCollisionData,
        private val frontFieldDataAndFieldCollisionData: FieldDataAndFieldCollisionData? = null
) {
    constructor(fieldName: FieldName,
                backFieldDataAndFieldCollisionData: FieldDataAndFieldCollisionData,
                frontFieldDataAndFieldCollisionData: FieldDataAndFieldCollisionData? = null) : this(fieldName, null, backFieldDataAndFieldCollisionData, frontFieldDataAndFieldCollisionData)

    val backFieldLayer = FieldLayer(fieldName, FieldLayerNameType.back, backFieldDataAndFieldCollisionData)
    val frontFieldLayer: FieldLayer?  = if(frontFieldDataAndFieldCollisionData != null) {
        FieldLayer(fieldName, FieldLayerNameType.front, frontFieldDataAndFieldCollisionData)
    } else {
        null
    }

    fun getBackFieldValue(position: Position): BackFieldValue {
        if(position.fieldName.value != fieldName.value) {
            throw RuntimeException("フィールド名が違います")
        }
        return BackFieldValue(backFieldLayer.fieldData.getPositionValue(position.x, position.y))
    }

    fun getFrontFieldValue(position: Position): FrontFieldValue? {
        if(position.fieldName.value != fieldName.value) {
            throw RuntimeException("フィールド名が違います")
        }
        return frontFieldLayer?.let { FrontFieldValue(it.fieldData.getPositionValue(position.x, position.y)) }
    }

    fun getUpperFieldValue(position: Position): FieldValue {
        val result = getFrontFieldValue(position)
        if(result != null && result.value > 0) {
            return result
        }

        return getBackFieldValue(position);
    }
}

open class FieldValue(override val value: Int): ValueImutable<Int>
class BackFieldValue(fieldValue: FieldValue): FieldValue(fieldValue.value)
class FrontFieldValue(fieldValue: FieldValue): FieldValue(fieldValue.value)