package com.naosim.rpgmodel.sirokuro

import android.util.Log
import com.naosim.rpgmodel.lib.script.MessageScriptController
import com.naosim.rpgmodel.lib.value.field.PositionAndDirection
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModel
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModelFactory
import com.naosim.rpgmodel.sirokuro.charactor.*
import com.naosim.rpgmodel.sirokuro.global.DataSaveRepository
import com.naosim.rpgmodel.sirokuro.global.GlobalContainer
import com.naosim.rpgmodel.sirokuro.map.YagiFieldMap
import com.naosim.rpgmodel.sirokuro.map.jump

class SirokuroGame(
        val fieldViewModelFactory: FieldViewModelFactory,
        val messageScriptController: MessageScriptController,
        val dataSaveRepository: DataSaveRepository
) {
    val fieldViewModel: FieldViewModel
    val kuro: KuroYagi
    val siro: SiroYagi
    val player: Player
    val yagiFieldMap = YagiFieldMap()

    var isJump = false;

    val globalContainer: GlobalContainer

    init {
        val dataSaveContainer = dataSaveRepository.load()
        this.fieldViewModel = fieldViewModelFactory.create(
                {
                    initFieldViewModel(it)
                },
                { fieldViewModel: FieldViewModel, positionAndDirection: PositionAndDirection ->
                    updatePositionAndDirection(positionAndDirection)
                }
        )

        this.globalContainer = GlobalContainer(
                messageScriptController,
                dataSaveContainer.status,
                dataSaveContainer.itemSet,
                this.fieldViewModel,
                dataSaveContainer.position
        )

        this.kuro = KuroYagi(globalContainer)
        this.siro = SiroYagi(globalContainer)
        this.player = Player(globalContainer)
    }

    fun onDestroy() {
        Log.e(this.javaClass.simpleName, "onDestroy")
        dataSaveRepository.save(globalContainer.getDataSaveContainer())
    }

    fun onPressAButton() {
        fieldViewModel.getPositionAndDirection {
            when(yagiFieldMap.getCheckEventTarget(it)) {
                EventTargetType.kuro -> kuro.check()
                EventTargetType.siro -> siro.check()
            }
        }
    }

    fun onItemUsed(gameItem: GameItem) {
        fieldViewModel.getPositionAndDirection {
            when(yagiFieldMap.getCheckEventTarget(it)) {
                EventTargetType.kuro -> kuro.useItem(gameItem)
                EventTargetType.siro -> siro.useItem(gameItem)
                else -> player.useItem(gameItem)
            }
        }
    }

    fun initFieldViewModel(fieldViewModel: FieldViewModel) {
        val position = globalContainer.lastPosition
        fieldViewModel.updateFieldAndGo(
                yagiFieldMap.getField(position.fieldName),
                position.x,
                position.y
        )
    }

    fun updatePositionAndDirection(positionAndDirection: PositionAndDirection) {
        val position = positionAndDirection.position
        globalContainer.lastPosition = position
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

}