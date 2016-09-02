package com.naosim.rpglib.model.value.field

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
}