package com.naosim.rpgmodel.lib.model

import com.naosim.rpgmodel.lib.model.value.Item
import com.naosim.rpgmodel.lib.model.viewmodel.FieldViewModel

interface GameMain {
    val fieldViewModel: FieldViewModel
    fun getItemList(): List<Item>
    fun onPressAButton()
    fun onItemUsed(item: Item)
    fun onDestroy()
}