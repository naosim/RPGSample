package com.naosim.rpg.model.mogura

import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.ItemId
import com.naosim.rpglib.model.value.ItemName

enum class MoguraItem: Item {
    hoge, letter;

    override val itemId: ItemId = ItemId("IID_${name}")
    override val itemName: ItemName = ItemName(name)
}