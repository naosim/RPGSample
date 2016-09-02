package com.naosim.rpgmodel.sirokuro.charactor

import com.naosim.rpglib.model.EventableObject
import com.naosim.rpglib.model.script.ScriptUtil
import com.naosim.rpglib.model.gametool.GlobalContainer
import com.naosim.rpgmodel.sirokuro.global.Status

abstract class EventableObjectCommon(protected val globalContainer: GlobalContainer<Status, GameItem>): EventableObject<GameItem> {
    protected val scriptUtil = ScriptUtil(globalContainer.scriptExecutor)
}