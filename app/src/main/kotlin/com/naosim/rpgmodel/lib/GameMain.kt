package com.naosim.rpgmodel.lib

import com.naosim.rpgmodel.lib.value.Item
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModel

interface GameMain {
    val fieldViewModel: FieldViewModel
    fun getItemList(): List<Item>
    fun onPressAButton()
    fun onItemUsed(item: Item)
    open fun onDestroy()
}