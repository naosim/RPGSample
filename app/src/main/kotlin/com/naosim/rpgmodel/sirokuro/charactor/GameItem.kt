package com.naosim.rpgmodel.sirokuro.charactor

import com.naosim.rpgmodel.lib.value.Item
import com.naosim.rpgmodel.lib.value.ItemId
import com.naosim.rpgmodel.lib.value.ItemName

enum class GameItem(): Item {
    やくそう, くろやぎさんの手紙, しろやぎさんの手紙;
    override val itemId = ItemId("IID${this.ordinal}")
    override val itemName = ItemName(this.name)
}

fun getGameItem(itemId: ItemId): GameItem {
    return GameItem.values().filter { it.itemId.value == itemId.value }.get(0)
}
