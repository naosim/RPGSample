package com.naosim.rpglib.model.gametool

import com.naosim.rpglib.model.script.ScriptExecutor
import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.ItemSet
import com.naosim.rpglib.model.value.field.Position
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModel
import com.naosim.rpglib.model.viewmodel.sound.bgm.BGMPlayModel
import com.naosim.rpglib.model.viewmodel.sound.se.SEPlayModel

open class GlobalContainer<S, I: Item>(
        val status: S,
        val itemSet: ItemSet<I>,
        val globalCommonContainer: GlobalCommonContainer
) {
    val scriptExecutor: ScriptExecutor = globalCommonContainer.scriptExecutor
    val fieldViewModel: FieldViewModel = globalCommonContainer.fieldViewModel
    val bgmPlayModel: BGMPlayModel = globalCommonContainer.bgmPlayModel
    val sePlayModel: SEPlayModel = globalCommonContainer.sePlayModel

    var lastPosition: Position
        get() {
            return globalCommonContainer.lastPosition
        }
        set(value) {
            globalCommonContainer.lastPosition = value
        }

    fun getDataSaveContainer(): DataSaveContainer<S, I> {
        return DataSaveContainer(status, itemSet, this.lastPosition)
    }
}

open class GlobalCommonContainer(
        val scriptExecutor: ScriptExecutor,
        val fieldViewModel: FieldViewModel,
        var lastPosition: Position,
        val bgmPlayModel: BGMPlayModel,
        val sePlayModel: SEPlayModel
)