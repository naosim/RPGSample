package com.naosim.rpgmodel.lib.model.value.field

import com.naosim.rpgmodel.lib.model.viewmodel.sound.bgm.HasBGM

class Field(
        val fieldName: FieldName,
        val hasBGM: HasBGM? = null,
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