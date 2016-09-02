package com.naosim.rpgmodel.sirokuro.charactor

import com.naosim.rpglib.model.gametool.GlobalContainer
import com.naosim.rpgmodel.sirokuro.global.Status
import com.naosim.rpgmodel.sirokuro.global.Turn

class SiroYagi(globalContainer: GlobalContainer<Status, GameItem>): EventableObjectCommon(globalContainer) {
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
                        {
                            globalContainer.itemSet.add(GameItem.しろやぎさんの手紙)
                            globalContainer.status.turnValue.next()
                        }
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
                        ${self}「渡してくれてありがとう！[r]
                        おれいにやくそうをあげるよ
                        」
                        """
                ,{globalContainer.itemSet.add(GameItem.やくそう)})
            }

            Turn.kuro_write -> {
                scriptUtil.script(
                        """
                        ${self}「${other}さんからお手紙？
                        ありがとう。
                        それにしてもお腹すいたなぁ」
                        """,
                        {
                            globalContainer.itemSet.remove(GameItem.くろやぎさんの手紙)
                            globalContainer.status.turnValue.next()
                        }
                )
            }
        }
    }

    override fun useItem(item: GameItem): Boolean {
        if(item == GameItem.くろやぎさんの手紙) {
            check()
            return true
        }
        return false
    }
}