package com.naosim.rpgmodel.lib.value.field

class Field(
        val fieldName: FieldName,
        private val backFieldDataAndFieldCollisionData: FieldDataAndFieldCollisionData,
        private val frontFieldDataAndFieldCollisionData: FieldDataAndFieldCollisionData? = null
) {
    val backFieldLayer = FieldLayer(fieldName, FieldLayerNameType.back, backFieldDataAndFieldCollisionData)
    val frontFieldLayer: FieldLayer?  = if(frontFieldDataAndFieldCollisionData != null) {
        FieldLayer(fieldName, FieldLayerNameType.front, frontFieldDataAndFieldCollisionData)
    } else {
        null
    }
}