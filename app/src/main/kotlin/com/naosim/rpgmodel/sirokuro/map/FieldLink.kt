package com.naosim.rpgmodel.sirokuro.map

import com.naosim.rpglib.model.value.field.Position
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModel
import com.naosim.rpglib.model.viewmodel.sound.bgm.BGMPlayModel
import com.naosim.rpglib.model.viewmodel.sound.se.SEPlayModel
import com.naosim.rpgmodel.sirokuro.SirokuroSE

class FieldLink(val position1: Position, val position2: Position)

fun jump(currentPos: Position, fieldViewModel: FieldViewModel, bgmPlayModel: BGMPlayModel, sePlayModel: SEPlayModel, yagiFieldMap: YagiFieldMap, list: List<FieldLink>): Boolean {
    var result = false
    //left
    list
            .filter({ it.position1.isSame(currentPos)})
            .map({ it.position2 })
            .forEach {
                val field = yagiFieldMap.getField(it.fieldName)
                fieldViewModel.updateFieldAndGo(field, it.x, it.y)
                if(field.hasBGM != null) {
                    bgmPlayModel.play(field.hasBGM!!)
                } else {
                    bgmPlayModel.stop()
                }
                sePlayModel.play(SirokuroSE.se1)

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
                    bgmPlayModel.play(field.hasBGM!!)
                } else {
                    bgmPlayModel.stop()
                }
                sePlayModel.play(SirokuroSE.se1)

                result = true
            }

    return result


}