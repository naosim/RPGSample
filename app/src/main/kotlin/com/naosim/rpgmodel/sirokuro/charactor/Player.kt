package com.naosim.rpgmodel.sirokuro.charactor

import com.naosim.rpgmodel.sirokuro.global.GlobalContainer

class Player(globalContainer: GlobalContainer): EventableObjectCommon(globalContainer) {
    override fun check() {
    }

    override fun useItem(item: GameItem): Boolean {
        scriptUtil.script(
                """
                やくそうを使った[r]
                これはまずい。
                """
        )
        globalContainer.itemSet.remove(item)
        return true
    }

}