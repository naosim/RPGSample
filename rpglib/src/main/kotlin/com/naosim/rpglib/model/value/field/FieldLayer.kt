package com.naosim.rpglib.model.value.field

class FieldLayer(
        val fieldName: FieldName,
        val fieldLayerName: FieldLayerName,
        val fieldDataAndFieldCollisionData: FieldDataAndFieldCollisionData
) {
    val fieldData = fieldDataAndFieldCollisionData.fieldData
    val fieldCollisionData = fieldDataAndFieldCollisionData.fieldCollisionData
}

fun createFieldLayer(
        fieldName: String,
        fieldLayerName: FieldLayerName,
        fieldData: String,
        fieldCollisionData: String? = null): FieldLayer {
    return FieldLayer(
            FieldNameImpl(fieldName),
            fieldLayerName,
            FieldDataAndFieldCollisionData(
                    createFieldData(fieldData),
                    if (fieldCollisionData != null) createFieldCollisionData(fieldCollisionData) else null
            )
    )
}

fun createFieldLayer(
        fieldName: FieldName,
        fieldLayerName: FieldLayerName,
        fieldData: String,
        fieldCollisionData: String? = null): FieldLayer {
    return createFieldLayer(
            fieldName.value,
            fieldLayerName,
            fieldData,
            fieldCollisionData
    )
}
