package com.naosim.rpglib.model.gametool

import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.ItemSet
import com.naosim.rpglib.model.value.field.Position

interface DataSaveRepository<S, I: Item> {
    fun save(dataSaveContainer: DataSaveContainer<S, I>)
    fun load(): DataSaveContainer<S, I>
}

class DataSaveContainer<S, I: Item>(
        val status: S,
        val itemSet: ItemSet<I>,
        val position: Position
)