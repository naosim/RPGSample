package com.naosim.rpgmodel.sirokuro.global

import com.naosim.rpgmodel.lib.script.ScriptExecutor
import com.naosim.rpgmodel.lib.value.ItemSet
import com.naosim.rpgmodel.sirokuro.charactor.GameItem

class GlobalContainer(val scriptExecutor: ScriptExecutor, val status: Status, val itemSet: ItemSet<GameItem>)