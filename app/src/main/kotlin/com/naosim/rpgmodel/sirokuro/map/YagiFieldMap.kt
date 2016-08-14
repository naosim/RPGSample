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

//    val map1 = createFieldLayer(
//            YagiFieldName.main,
//            FieldLayerNameType.back, """
//0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
//0,0,2,22,2,0,0,0,0,0,0,0,0,2,22,2,0,0
//0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0
//0,0,2,2,2,0,0,0,0,0,0,0,0,2,2,2,0,0
//0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
//""")

    val kuroField = Field(
            YagiFieldName.kuro,
            FieldDataAndFieldCollisionData(
                    createFieldData(
                            """
13,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
"""
                    )
            )
    )

    val siroField = Field(
            YagiFieldName.siro,
            FieldDataAndFieldCollisionData(
                    createFieldData(
                            """
13,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
"""
                    )
            )
    )



//    val map2 = createFieldLayer(
//            YagiFieldName.kuro,
//            FieldLayerNameType.back, """
//13,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
//2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
//2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
//2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
//2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
//""")
//
//    val map3 = createFieldLayer(
//            YagiFieldName.siro,
//            FieldLayerNameType.back, """
//13,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
//2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
//2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
//2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
//2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0
//""")


    val linkList: List<FieldLink>
    init {
        linkList = ArrayList<FieldLink>()
        linkList.add(FieldLink(Position(YagiFieldName.main, X(3), Y(1)), Position(YagiFieldName.kuro, X(0), Y(0))))
        linkList.add(FieldLink(Position(YagiFieldName.main, X(14), Y(1)), Position(YagiFieldName.siro, X(0), Y(0))))
    }



}

