package com.naosim.rpglib.model.viewmodel

import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.ItemSet

interface ItemSelectorViewModel<I: Item> {
    val itemSet: ItemSet<I>
    var onSelectedItem: (I)->Unit
}