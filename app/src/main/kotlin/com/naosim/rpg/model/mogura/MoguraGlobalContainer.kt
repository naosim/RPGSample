package com.naosim.rpg.model.mogura

import com.naosim.rpglib.model.value.ItemSet
import com.naosim.rpglib.model.gametool.GlobalCommonContainer
import com.naosim.rpglib.model.gametool.GlobalContainer

class MoguraGlobalContainer(
        status: MoguraStatus,
        itemSet: ItemSet<MoguraItem>,
        globalCommonContainer: GlobalCommonContainer
) : GlobalContainer<MoguraStatus, MoguraItem>(status, itemSet, globalCommonContainer)