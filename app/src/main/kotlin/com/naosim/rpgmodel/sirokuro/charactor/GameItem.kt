package com.naosim.rpgmodel.sirokuro.charactor

import com.naosim.rpgmodel.lib.value.Item
import com.naosim.rpgmodel.lib.value.ItemId
import com.naosim.rpgmodel.lib.value.ItemName

enum class GameItem(): Item {
    やくそう;
    override val itemId = ItemId("IID${this.ordinal}")
    override val itemName = ItemName(this.name)
}
