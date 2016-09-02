package com.naosim.rpgmodel.sirokuro.charactor

import com.naosim.rpglib.model.gametool.GlobalContainer
import com.naosim.rpglib.model.script.ScriptUtil
import com.naosim.rpgmodel.sirokuro.global.Status

class Player(globalContainer: GlobalContainer<Status, GameItem>): EventableObjectCommon(globalContainer) {
    override fun check() {
    }

    override fun useItem(gameItem: GameItem): Boolean {
        when(gameItem) {
            GameItem.やくそう -> useやくそう(gameItem, ScriptUtil(globalContainer.scriptExecutor))
            GameItem.くろやぎさんの手紙 -> useくろやぎさんの手紙(gameItem, ScriptUtil(globalContainer.scriptExecutor))
            GameItem.しろやぎさんの手紙 -> useしろやぎさんの手紙(gameItem, ScriptUtil(globalContainer.scriptExecutor))
        }
        return true
    }

    fun useやくそう(gameItem: GameItem, scriptUtil: ScriptUtil) {
        scriptUtil.script("""
        やくそうを食べた[r]
        ただのマズい草だった
        """, {globalContainer.itemSet.remove(gameItem)})
    }

    fun useくろやぎさんの手紙(gameItem: GameItem, scriptUtil: ScriptUtil) {
        scriptUtil.script("""
        しろやぎさんにとどけてほしいと
        くろやぎさんに頼まれた手紙
        """)
    }

    fun useしろやぎさんの手紙(gameItem: GameItem, scriptUtil: ScriptUtil) {
        scriptUtil.script("""
        くろやぎさんにとどけてほしいと
        しろやぎさんに頼まれた手紙
        """)
    }

}