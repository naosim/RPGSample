package com.naosim.rpg.model.mogura.map.b2

import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.map.MoguraFieldName
import com.naosim.rpglib.model.value.field.*

class MoguraB2FieldFactory(val globalContainer: MoguraGlobalContainer) {

    fun createBackFieldData(): FieldData {
        return if(!globalContainer.status.earthquake.getValue()) {
            createFieldData("""
             3, 4, 4, 4, 4, 3
             3, 5, 5, 5, 5, 3
             3, 5, 5, 5, 5, 3
             3, 5, 5, 5, 5, 3
             3, 5, 5, 5, 5, 3
             4, 4, 4, 4, 4, 4
             """)

        } else {
            createFieldData("""
             3, 4, 4, 4, 4, 3,-1,-1,-1,-1, 6, 6,-1,-1,-1,-1
             3, 5, 5, 5, 5, 3,-1,-1,-1,-1, 6, 6,-1,-1,-1,-1
             3, 5, 5, 5, 5, 4,-1,-1,-1,-1, 6, 6,-1,-1,-1,-1
             3, 5, 5, 5, 5, 6, 6,-1,-1,-1, 6, 6,-1,-1,-1,-1
             3, 5, 5, 5, 6, 6, 6, 6,-1,-1, 6, 6,-1,-1,-1,-1
             4, 4, 4, 4, 4, 4, 6, 6,-1, 6, 6, 6, 6,-1,-1,-1
            -1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,-1,-1,-1
            -1, 6,-1,-1,-1,-1,-1, 6, 6, 6, 6, 6, 6,-1,-1,-1
            -1, 6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
            -1, 6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
            -1, 6, 6, 6, 6, 6, 6, 6, 6,-1,-1,-1,-1,-1,-1,-1
            -1, 6,-1,-1,-1, 6, 6, 6, 6,-1,-1,-1,-1,-1,-1,-1
            -1, 6,-1,-1,-1, 6, 6, 6, 6,-1,-1,-1,-1,-1,-1,-1
            -1, 6,-1,-1,-1, 6, 6, 6, 6,-1,-1,-1,-1,-1,-1,-1
            -1, 6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
    """)
        }
    }

    fun createBackCollisionData(): FieldCollisionData {
        val list2d = createBackFieldData().value.map { row -> row.map { cell ->
            when(cell) {
                3, 4, 7, 9 -> 1 // 当たり判定あり
                else -> 0
            }
        }.toMutableList() }
        return FieldCollisionData(list2d)
    }

    fun createFrontFieldDataString(): String {
        return if(!globalContainer.status.earthquake.getValue()) {
            """
-1,-1,-1,-1,-1,-1
-1,36,-1,-1,14,-1
-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1
-1,13,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1
    """
        } else {
            """
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,13,-1,-1,-1,-1
-1,36,-1,27,14,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,27,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,27,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,13,27,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,18,18,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,18,18,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,14,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
    """
        }
    }

    fun createFrontFieldData(): FieldData {
        return createFieldData(createFrontFieldDataString())
    }

    fun createFrontCollisionData(): FieldCollisionData {
        val list2d = createFrontFieldData().value.map { row -> row.map { cell ->
            when(cell) {
                3, 4, 27, 36 -> 1 // 当たり判定あり
                else -> 0
            }
        }.toMutableList() }
        return FieldCollisionData(list2d)
    }

    fun createField(): Field {
        return Field(
                MoguraFieldName.b2,
                FieldDataAndFieldCollisionData(
                        createBackFieldData(),
                        createBackCollisionData()
                ),
                FieldDataAndFieldCollisionData(
                        createFrontFieldData(),
                        createFrontCollisionData()
                )
        )
    }
}

