package com.naosim.rpglib.model

import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModel

interface GameMain<F: FieldViewModel> {
    val fieldViewModel: F
    fun getItemList(): List<Item>
    fun onPressAButton()
    fun onItemUsed(item: Item)
    fun onStart(){}
    fun onStop(){}
    fun onDestroy(){}
}