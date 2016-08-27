package com.naosim.rpgmodel.sirokuro.map

import com.naosim.rpgmodel.lib.model.value.field.Position
import com.naosim.rpgmodel.lib.model.viewmodel.BGMPlayModel
import com.naosim.rpgmodel.lib.model.viewmodel.FieldViewModel

class FieldLink(val position1: Position, val position2: Position)

fun jump(currentPos: Position, fieldViewModel: FieldViewModel, bgmPlayModel: BGMPlayModel, yagiFieldMap: YagiFieldMap, list: List<FieldLink>): Boolean {
    var result = false
    //left
    list
            .filter({ it.position1.isSame(currentPos)})
            .map({ it.position2 })
            .forEach {
                val field = yagiFieldMap.getField(it.fieldName)
                fieldViewModel.updateFieldAndGo(field, it.x, it.y)
                if(field.hasBGM != null) {
                    bgmPlayModel.play(field.hasBGM)
                } else {
                    bgmPlayModel.stop()
                }

                result = true
            }

    // right
    list
            .filter({ it.position2.isSame(currentPos)})
            .map({ it.position1 })
            .forEach {
                val field = yagiFieldMap.getField(it.fieldName)
                fieldViewModel.updateFieldAndGo(field, it.x, it.y)
                if(field.hasBGM != null) {
                    bgmPlayModel.play(field.hasBGM)
                } else {
                    bgmPlayModel.stop()
                }
                result = true
            }

    return result


}