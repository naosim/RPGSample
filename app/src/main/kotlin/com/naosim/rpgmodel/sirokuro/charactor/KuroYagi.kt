package com.naosim.rpgmodel.sirokuro.charactor

import com.naosim.rpgmodel.sirokuro.global.GlobalContainer
import com.naosim.rpgmodel.sirokuro.global.Turn

class KuroYagi(globalContainer: GlobalContainer): EventableObjectCommon(globalContainer) {
    override fun check() {
        val self = "黒ヤギ"
        val other = "白ヤギ"
        when(globalContainer.status.turnValue.getValue()) {
            Turn.kuro_eat -> {
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

            Turn.kuro_write -> {
                scriptUtil.script(
                        """
                        ${self}「手紙、渡してね。頼むよ。[r]
                        それにしても何の用事だったんだろう。」
                        """
                )
            }

            Turn.siro_eat -> {
                scriptUtil.script(
                        """
                        ${self}「渡してくれてありがとう！」
                        """
                )
            }

            Turn.siro_write -> {
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