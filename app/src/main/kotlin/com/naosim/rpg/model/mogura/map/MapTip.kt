package com.naosim.rpg.model.mogura.map

import com.naosim.rpglib.model.value.field.FieldValue

enum class MapTip(val value: Int) {
    コンクリ壁_上(3),
    コンクリ壁_正面(4),
    上り階段(13),
    下り階段(14),
    テーブル(15);

    fun eq(fieldValue: FieldValue): Boolean {
        return mapEq(fieldValue, this)
    }

    fun notEq(fieldValue: FieldValue): Boolean {
        return !eq(fieldValue)
    }

}

fun mapEq(fieldValue: FieldValue, mapTip: MapTip): Boolean {
    return fieldValue.value == mapTip.value
}
fun mapNotEq(fieldValue: FieldValue, mapTip: MapTip): Boolean {
    return !mapEq(fieldValue, mapTip)
}