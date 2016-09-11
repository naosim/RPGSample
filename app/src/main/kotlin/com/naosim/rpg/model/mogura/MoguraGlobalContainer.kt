package com.naosim.rpg.model.mogura

import com.naosim.rpglib.model.gametool.GlobalCommonContainer
import com.naosim.rpglib.model.gametool.GlobalContainer
import com.naosim.rpglib.model.script.ScriptUtil
import com.naosim.rpglib.model.value.ItemSet

class MoguraGlobalContainer(
        status: MoguraStatus,
        itemSet: ItemSet<MoguraItem>,
        globalCommonContainer: GlobalCommonContainer
) : GlobalContainer<MoguraStatus, MoguraItem>(status, itemSet, globalCommonContainer) {
    val scriptUtil = ScriptUtil(scriptExecutor)

    fun hasItem(item: MoguraItem): Boolean {
        return itemSet.has(item)
    }
    fun notHaveItem(item: MoguraItem): Boolean {
        return !hasItem(item)
    }
}