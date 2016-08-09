package com.naosim.rpgmodel.lib.viewmodel

import com.naosim.rpgmodel.lib.value.Item
import com.naosim.rpgmodel.lib.value.ItemSet

interface ItemSelectorViewModel<I: Item> {
    val itemSet: ItemSet<I>
    var onSelectedItem: (I)->Unit
}