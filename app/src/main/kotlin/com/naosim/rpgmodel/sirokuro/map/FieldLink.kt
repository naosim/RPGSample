package com.naosim.rpgmodel.sirokuro.map

import com.naosim.rpgmodel.lib.value.field.Position
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModel

class FieldLink(val position1: Position, val position2: Position)

fun jump(currentPos: Position, fieldViewModel: FieldViewModel, yagiFieldMap: YagiFieldMap, list: List<FieldLink>): Boolean {
    var result = false
    //left
    list
            .filter({ it.position1.isSame(currentPos)})
            .map({ it.position2 })
            .forEach {
                fieldViewModel.updateFieldAndGo(yagiFieldMap.getField(it.fieldName), it.x, it.y)
                result = true
            }

    // right
    list
            .filter({ it.position2.isSame(currentPos)})
            .map({ it.position1 })
            .forEach {
                fieldViewModel.updateFieldAndGo(yagiFieldMap.getField(it.fieldName), it.x, it.y)
                result = true
            }

    return result


}