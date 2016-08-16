package com.naosim.rpgmodel.sirokuro

import android.util.Log
import com.naosim.rpgmodel.lib.script.MessageScriptController
import com.naosim.rpgmodel.lib.value.field.PositionAndDirection
import com.naosim.rpgmodel.lib.value.field.X
import com.naosim.rpgmodel.lib.value.field.Y
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModel
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModelFactory
import com.naosim.rpgmodel.sirokuro.charactor.EventTargetType
import com.naosim.rpgmodel.sirokuro.charactor.KuroYagi
import com.naosim.rpgmodel.sirokuro.charactor.Player
import com.naosim.rpgmodel.sirokuro.charactor.SiroYagi
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

    private val globalContainer: GlobalContainer

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

        val dataSaveContainer = dataSaveRepository.load();

        this.globalContainer = GlobalContainer(
                messageScriptController,
                dataSaveContainer.status,
                dataSaveContainer.itemSet,
                this.fieldViewModel
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
            val position = it.position
            Log.e("SirokuroGame", "${position.fieldName.value}:${position.x.value}, ${position.y.value}, ${it.direction.name}")
            yagiFieldMap.getCheckEventTarget(it)?.let {
                when(it) {
                    EventTargetType.kuro -> kuro.check()
                    EventTargetType.siro -> siro.check()
                }

            }
        }

    }

}