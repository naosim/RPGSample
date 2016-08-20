package com.naosim.rpgmodel.sirokuro.global

import com.naosim.rpgmodel.lib.value.ItemSet
import com.naosim.rpgmodel.lib.value.field.Position
import com.naosim.rpgmodel.sirokuro.charactor.GameItem

interface DataSaveRepository {
    fun save(status: DataSaveContainer)
    fun load(): DataSaveContainer
}

class DataSaveContainer(val status: Status, val itemSet: ItemSet<GameItem>, val position: Position)