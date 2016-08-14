package com.naosim.rpgmodel.sirokuro

import android.util.Log
import com.naosim.rpgmodel.lib.script.MessageScriptController
import com.naosim.rpgmodel.lib.value.ItemSet
import com.naosim.rpgmodel.lib.value.field.PositionAndDirection
import com.naosim.rpgmodel.lib.value.field.X
import com.naosim.rpgmodel.lib.value.field.Y
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModel
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModelFactory
import com.naosim.rpgmodel.sirokuro.charactor.GameItem
import com.naosim.rpgmodel.sirokuro.charactor.KuroYagi
import com.naosim.rpgmodel.sirokuro.charactor.Player
import com.naosim.rpgmodel.sirokuro.charactor.SiroYagi
import com.naosim.rpgmodel.sirokuro.global.GlobalContainer
import com.naosim.rpgmodel.sirokuro.global.Status
import com.naosim.rpgmodel.sirokuro.map.YagiFieldMap
import com.naosim.rpgmodel.sirokuro.map.jump

class SirokuroGame(
        val fieldViewModelFactory: FieldViewModelFactory,
        val messageScriptController: MessageScriptController
) {
    val fieldViewModel: FieldViewModel
    val kuro: KuroYagi
    val siro: SiroYagi
    val player: Player
    val yagiFieldMap = YagiFieldMap()

    var isJump = false;

            init {


                this.fieldViewModel = fieldViewModelFactory.create(
                        {
                            Log.e("SirokuroGame", "onload")
                            it.updateFieldAndGo(yagiFieldMap.mainField, X(0), Y(0))
                        },
                        { fieldViewModel: FieldViewModel, positionAndDirection: PositionAndDirection ->
                            val position = positionAndDirection.position
                            if(!isJump) {
                                Log.e("SirokuroGame", "${position.fieldName.value}:${position.x.value}, ${position.y.value}, ${positionAndDirection.direction.name}")

                                isJump = jump(
                                        position,
                                        fieldViewModel,
                                        yagiFieldMap,
                                        yagiFieldMap.linkList
                                )

                            } else {
                                isJump = false
                            }

                        }
                )

                val itemSet = ItemSet<GameItem>()
                itemSet.add(GameItem.やくそう)
                itemSet.add(GameItem.やくそう)
                itemSet.add(GameItem.やくそう)

                val gc = GlobalContainer(messageScriptController, Status(), itemSet, this.fieldViewModel)

                this.kuro = KuroYagi(gc)
                this.siro = SiroYagi(gc)
                this.player = Player(gc)
    }

    fun onPressAButton() {
        fieldViewModel.getPositionAndDirection {
            val position = it.position
            Log.e("SirokuroGame", "${position.fieldName.value}:${position.x.value}, ${position.y.value}, ${it.direction.name}")
        }

    }

}