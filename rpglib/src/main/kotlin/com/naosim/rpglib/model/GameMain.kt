package com.naosim.rpglib.model

import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.viewmodel.FieldViewModel

interface GameMain {
    val fieldViewModel: FieldViewModel
    fun getItemList(): List<Item>
    fun onPressAButton()
    fun onItemUsed(item: Item)
    fun onStart(){}
    fun onStop(){}
    fun onDestroy(){}
}