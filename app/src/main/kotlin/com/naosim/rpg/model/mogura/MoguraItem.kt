package com.naosim.rpg.model.mogura

import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.ItemId
import com.naosim.rpglib.model.value.ItemName

enum class MoguraItem: Item {
    hoge, 父親のメモ;

    override val itemId: ItemId = ItemId("IID_${name}")
    override val itemName: ItemName = ItemName(name)

}

fun getMoguraItem(itemId: ItemId): MoguraItem {
    return MoguraItem.values().filter { it.itemId.value == itemId.value }.first()
}