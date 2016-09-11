package com.naosim.rpg.model.mogura.map.b1

import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.map.MoguraFieldName
import com.naosim.rpg.model.mogura.map.MoguraMapEvent
import com.naosim.rpglib.model.value.field.Field
import com.naosim.rpglib.model.value.field.FieldCollisionData
import com.naosim.rpglib.model.value.field.FieldDataAndFieldCollisionData
import com.naosim.rpglib.model.value.field.createFieldData

class MoguraB1FieldFactory(val globalContainer: MoguraGlobalContainer) {
    val backFieldData = createFieldData("""
 3, 4, 4, 4, 4, 4, 4, 4, 3,14, 9,-1,-1,-1,-1,-1
 3, 5, 5, 5, 5, 5, 5, 5, 3,-1, 9,-1,-1,-1,14,-1
 3, 5, 5, 5, 5, 5, 5, 5, 3,-1, 9,-1,-1,-1, 6,-1
 3, 5, 5, 5, 5, 5, 5, 5, 3,-1, 9,-1,-1,-1, 6,-1
 3, 5, 5, 5, 5, 5, 5, 5, 3,-1, 9,-1,-1,-1, 6,-1
 3, 5, 5, 5, 5, 5, 5, 5, 3,-1, 9,-1,-1,-1, 6,-1
 3, 5, 5, 5, 5, 5, 5, 5, 3,-1, 9,-1,-1,-1, 6,-1
 4, 4, 5, 4, 4, 4, 4, 4, 4,-1, 9,-1,-1,-1, 6,-1
-1, 9,-1,-1,-1,-1,-1,-1,-1,-1, 9,-1,-1,-1, 6,-1
-1,-1, 9, 9, 9, 9, 9, 9, 9, 9,-1,-1,-1,-1, 6,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 6,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 6,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
    """)

    fun createBackCollisionData(): FieldCollisionData {
        val list2d = backFieldData.value.map { row -> row.map { cell ->
            when(cell) {
                3, 4, 7, 9 -> 1 // 当たり判定あり
                else -> 0
            }
        }.toMutableList() }
        return FieldCollisionData(list2d)
    }



    val frontFieldData = createFieldData("""
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,31,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,31,-1,-1,-1, 9,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,31,-1,-1,-1, 9,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,31,-1,-1,-1, 9,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,31,-1,-1,-1, 9,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,31,-1,-1,-1, 9,-1
-1,-1,-1,-1,-1,-1,-1,13,-1,-1,31,-1,-1,-1, 9,-1
-1,-1, 4,-1,-1,-1,-1,-1,-1,-1,31,-1,-1,-1, 9,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,31,-1,-1,-1, 9,-1
31,31,31,31,31,31,31,31,31,31,31,-1,-1,-1, 9,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 9,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 9,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
    """)

    fun createFrontCollisionData(): FieldCollisionData {
        val list2d = frontFieldData.value.map { row -> row.map { cell ->
            0
        }.toMutableList() }
        return FieldCollisionData(list2d)
    }

    fun create(eventCallback: (MoguraMapEvent)->Unit): MoguraB1FieldLogic {
        val field = Field(
                MoguraFieldName.b1,
                FieldDataAndFieldCollisionData(
                        backFieldData,
                        createBackCollisionData()
                ),
                FieldDataAndFieldCollisionData(
                        frontFieldData,
                        createFrontCollisionData()
                )
        )
        return MoguraB1FieldLogic(globalContainer, field, eventCallback)
    }
}

