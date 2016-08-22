package com.naosim.rpgmodel.lib.model.viewmodel

import com.naosim.rpgmodel.lib.model.value.Item
import com.naosim.rpgmodel.lib.model.value.ItemSet

interface ItemSelectorViewModel<I: Item> {
    val itemSet: ItemSet<I>
    var onSelectedItem: (I)->Unit
}