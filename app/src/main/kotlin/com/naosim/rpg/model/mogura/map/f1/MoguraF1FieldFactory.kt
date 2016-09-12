package com.naosim.rpg.model.mogura.map.f1

import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.map.MoguraFieldName
import com.naosim.rpglib.model.value.field.Field
import com.naosim.rpglib.model.value.field.FieldCollisionData
import com.naosim.rpglib.model.value.field.FieldDataAndFieldCollisionData
import com.naosim.rpglib.model.value.field.createFieldData

class MoguraF1FieldFactory(val globalContainer: MoguraGlobalContainer) {
    val backFieldData = createFieldData("""
0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7
7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 9, 7, 7
0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0
0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0
0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0
0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0
0, 3, 4, 4, 4, 4, 4, 4, 3, 0, 0, 0, 0, 2, 0, 0
0, 3,45, 5, 5, 5, 5, 5, 3, 0, 0, 0, 0, 2, 0, 0
0, 3, 5, 5, 5, 5, 5, 5, 3, 0, 0, 0, 0, 2, 0, 0
0, 3, 5, 5, 5, 5, 5, 5, 3, 0, 0, 0, 0, 2, 0, 0
0, 3, 5, 5, 5, 5, 5,14, 3, 0, 0, 0, 0, 2, 0, 0
0, 4, 4, 4, 5, 4, 4, 4, 4, 0, 0, 0, 0, 2, 0, 0
0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0
0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2
0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    """)

    fun createBackCollisionData(): FieldCollisionData {
        val list2d = backFieldData.value.map { row -> row.map { cell ->
            when(cell) {
                3, 4, 7 -> 1 // 当たり判定あり
                else -> 0
            }
        }.toMutableList() }
        return FieldCollisionData(list2d)
    }



    val frontFieldData = createFieldData("""
19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
19,19,19,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
19,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,29,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,15,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,24,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,18,18,18,-1,18,18,18,18,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
    """)

    fun createFrontCollisionData(): FieldCollisionData {
        val list2d = frontFieldData.value.map { row -> row.map { cell ->
            if(cell > 0) 1 else 0
        }.toMutableList() }
        return FieldCollisionData(list2d)
    }

    fun createField(): Field {
        return Field(
                MoguraFieldName.f1,
                FieldDataAndFieldCollisionData(
                        backFieldData,
                        createBackCollisionData()
                ),
                FieldDataAndFieldCollisionData(
                        frontFieldData,
                        createFrontCollisionData()
                )
        )
    }
}

