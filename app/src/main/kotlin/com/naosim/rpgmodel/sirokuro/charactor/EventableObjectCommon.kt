package com.naosim.rpgmodel.sirokuro.charactor

import com.naosim.rpgmodel.lib.model.EventableObject
import com.naosim.rpgmodel.lib.model.script.ScriptUtil
import com.naosim.rpgmodel.sirokuro.global.GlobalContainer

abstract class EventableObjectCommon(protected val globalContainer: GlobalContainer): EventableObject<GameItem> {
    protected val scriptUtil = ScriptUtil(globalContainer.scriptExecutor)
}