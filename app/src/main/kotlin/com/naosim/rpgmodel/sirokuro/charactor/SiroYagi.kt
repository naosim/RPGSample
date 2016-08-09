package com.naosim.rpgmodel.sirokuro.charactor

import com.naosim.rpgmodel.sirokuro.global.GlobalContainer
import com.naosim.rpgmodel.sirokuro.global.Turn

class SiroYagi(globalContainer: GlobalContainer): EventableObjectCommon(globalContainer) {
    override fun check() {
        val self = "白ヤギ"
        val other = "黒ヤギ"
        when(globalContainer.status.turnValue.getValue()) {
            Turn.siro_eat -> {
                scriptUtil.script(
                        """
                        ${self}「やあ。」[r]
                        ${self}「${other}さんからの手紙
                        読まずにたべちゃったから
                        お返しの手紙書いたんだ。
                        届けてくれる？」[r]
                        ${other}さんへの手紙を受け取った
                        """,
                        { globalContainer.status.turnValue.next() }
                )
            }

            Turn.siro_write -> {
                scriptUtil.script(
                        """
                        ${self}「手紙、渡してね。頼むよ。[r]
                        それにしても何の用事だったんだろう。」
                        """
                )
            }

            Turn.kuro_eat -> {
                scriptUtil.script(
                        """
                        ${self}「渡してくれてありがとう！」
                        """
                )
            }

            Turn.kuro_write -> {
                scriptUtil.script(
                        """
                        ${self}「${other}さんからお手紙？
                        ありがとう。
                        それにしてもお腹すいたなぁ」
                        """,
                        { globalContainer.status.turnValue.next() }
                )
            }
        }
    }
}