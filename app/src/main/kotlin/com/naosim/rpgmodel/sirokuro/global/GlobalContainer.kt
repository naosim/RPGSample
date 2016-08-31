package com.naosim.rpgmodel.sirokuro.global

import com.naosim.rpglib.model.script.ScriptExecutor
import com.naosim.rpglib.model.value.ItemSet
import com.naosim.rpglib.model.value.field.Position
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModel
import com.naosim.rpglib.model.viewmodel.sound.bgm.BGMPlayModel
import com.naosim.rpglib.model.viewmodel.sound.se.SEPlayModel
import com.naosim.rpgmodel.sirokuro.charactor.GameItem

class GlobalContainer(
        val scriptExecutor: ScriptExecutor,
        val status: Status,
        val itemSet: ItemSet<GameItem>,
        val fieldViewModel: FieldViewModel,
        var lastPosition: Position,
        val bgmPlayModel: BGMPlayModel,
        val sePlayModel: SEPlayModel
) {
    fun getDataSaveContainer(): DataSaveContainer {
        return DataSaveContainer(status, itemSet, lastPosition)
    }
}