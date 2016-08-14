package com.naosim.rpgmodel.sirokuro.map

import com.naosim.rpgmodel.lib.value.field.*
import java.util.*

enum class YagiFieldName: FieldName {
    main, siro, kuro;
    override val value = name
}

class YagiFieldMap {

    fun getField(fieldName: FieldName): Field {
        return when(fieldName.value) {
            YagiFieldName.main.value -> this.mainField
            YagiFieldName.kuro.value -> this.kuroField
            YagiFieldName.siro.value -> this.siroField
            else -> this.mainField
        }
    }

    val mainField = Field(
            YagiFieldName.main,
            // back
            FieldDataAndFieldCollisionData(
                    createFieldData(
                            """
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
0,0,2,2,2,0,0,0,0,0,0,0,0,2,2,2,0,0
0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0
0,0,2,2,2,0,0,0,0,0,0,0,0,2,2,2,0,0
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
"""
                    )
            ),
            FieldDataAndFieldCollisionData(
                    createFieldData(
                            """
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,22,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,22,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
"""
                    )
            )
    )

    val kuroField = Field(
            YagiFieldName.kuro,
            FieldDataAndFieldCollisionData(
                    createFieldData(
                            """
                            3,4,4,4,4,4,4,4,4,3
                            3,5,5,5,5,5,5,5,5,3
                            3,5,5,5,5,5,5,5,5,3
                            3,5,5,5,5,5,5,5,5,3
                            3,5,5,5,5,5,5,5,5,3
                            3,5,5,5,5,5,5,5,5,3
                            3,5,5,5,5,5,5,5,5,3
                            4,4,4,4,4,4,4,4,4,4
                            """
                    ),
                    createFieldCollisionData("""
                            1,1,1,1,1,1,1,1,1,1
                            1,0,0,0,0,0,0,0,0,1
                            1,0,0,0,0,0,0,0,0,1
                            1,0,0,0,0,0,0,0,0,1
                            1,0,0,0,0,0,0,0,0,1
                            1,0,0,0,0,0,0,0,0,1
                            1,0,0,0,0,0,0,0,0,1
                            1,1,1,1,1,1,1,1,1,1
                            """)
            ),
            FieldDataAndFieldCollisionData(
                    createFieldData("""
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,37,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,13,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            """),
                    createFieldCollisionData("""
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,1,0,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            """)
                    )

    )

    val siroField = Field(
            YagiFieldName.siro,
            FieldDataAndFieldCollisionData(
                    createFieldData(
                            """
                            3,4,4,4,4,4,4,4,4,3
                            3,5,5,5,5,5,5,5,5,3
                            3,5,5,5,5,5,5,5,5,3
                            3,5,5,5,5,5,5,5,5,3
                            3,5,5,5,5,5,5,5,5,3
                            3,5,5,5,5,5,5,5,5,3
                            3,5,5,5,5,5,5,5,5,3
                            4,4,4,4,4,4,4,4,4,4
                            """
                    ),
                    createFieldCollisionData("""
                            1,1,1,1,1,1,1,1,1,1
                            1,0,0,0,0,0,0,0,0,1
                            1,0,0,0,0,0,0,0,0,1
                            1,0,0,0,0,0,0,0,0,1
                            1,0,0,0,0,0,0,0,0,1
                            1,0,0,0,0,0,0,0,0,1
                            1,0,0,0,0,0,0,0,0,1
                            1,1,1,1,1,1,1,1,1,1
                            """)
            ),
            FieldDataAndFieldCollisionData(
                    createFieldData("""
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,38,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,13,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
                            """),
                    createFieldCollisionData("""
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,0,1,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            0,0,0,0,0,0,0,0,0,0
                            """)
            )
    )

    val linkList: List<FieldLink>
    init {
        linkList = ArrayList<FieldLink>()
        linkList.add(FieldLink(Position(YagiFieldName.main, X(3), Y(1)), Position(YagiFieldName.kuro, X(7), Y(6))))
        linkList.add(FieldLink(Position(YagiFieldName.main, X(14), Y(1)), Position(YagiFieldName.siro, X(2), Y(6))))
    }



}

