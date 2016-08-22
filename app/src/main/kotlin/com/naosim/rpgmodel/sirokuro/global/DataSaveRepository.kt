package com.naosim.rpgmodel.sirokuro.global

import com.naosim.rpgmodel.lib.model.value.ItemSet
import com.naosim.rpgmodel.lib.model.value.field.Position
import com.naosim.rpgmodel.sirokuro.charactor.GameItem

interface DataSaveRepository {
    fun save(status: DataSaveContainer)
    fun load(): DataSaveContainer
}

class DataSaveContainer(val status: Status, val itemSet: ItemSet<GameItem>, val position: Position)