package com.naosim.rpg.model.mogura

import com.naosim.rpglib.model.value.Item

class MoguraPlayer(moguraGlobalContainer: MoguraGlobalContainer) {
    val scriptUtil = moguraGlobalContainer.scriptUtil
    fun useItem(item: Item) {
        if(item.eq(MoguraItem.父親のメモ)) {
            scriptUtil.script(ScriptText.item_use_letter)
        }
    }
}