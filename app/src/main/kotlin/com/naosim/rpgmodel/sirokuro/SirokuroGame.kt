package com.naosim.rpgmodel.sirokuro

import android.util.Log
import com.naosim.rpgmodel.lib.model.GameMain
import com.naosim.rpgmodel.lib.model.script.MessageScriptController
import com.naosim.rpgmodel.lib.model.value.Item
import com.naosim.rpgmodel.lib.model.value.field.PositionAndDirection
import com.naosim.rpgmodel.lib.model.viewmodel.BGMPlayModel
import com.naosim.rpgmodel.lib.model.viewmodel.FieldViewModel
import com.naosim.rpgmodel.lib.model.viewmodel.FieldViewModelFactory
import com.naosim.rpgmodel.sirokuro.charactor.*
import com.naosim.rpgmodel.sirokuro.global.DataSaveRepository
import com.naosim.rpgmodel.sirokuro.global.GlobalContainer
import com.naosim.rpgmodel.sirokuro.map.YagiFieldMap
import com.naosim.rpgmodel.sirokuro.map.jump

class SirokuroGame(
        val fieldViewModelFactory: FieldViewModelFactory,
        val messageScriptController: MessageScriptController,
        val dataSaveRepository: DataSaveRepository,
        val bgmPlayModel: BGMPlayModel
): GameMain {
    override val fieldViewModel: FieldViewModel
    val kuro: KuroYagi
    val siro: SiroYagi
    val player: Player
    val yagiFieldMap = YagiFieldMap()

    var isJump = false;

    val globalContainer: GlobalContainer

    init {

        this.fieldViewModel = fieldViewModelFactory.create(
                {
                    initFieldViewModel(it)
                },
                { fieldViewModel: FieldViewModel, positionAndDirection: PositionAndDirection ->
                    updatePositionAndDirection(positionAndDirection)
                }
        )

        val dataSaveContainer = dataSaveRepository.load()
        this.globalContainer = GlobalContainer(
                messageScriptController,
                dataSaveContainer.status,
                dataSaveContainer.itemSet,
                this.fieldViewModel,
                dataSaveContainer.position,
                bgmPlayModel
        )

        this.kuro = KuroYagi(globalContainer)
        this.siro = SiroYagi(globalContainer)
        this.player = Player(globalContainer)
    }

    override fun onStart() {
        bgmPlayModel.restart()
    }

    override fun onStop() {
        dataSaveRepository.save(globalContainer.getDataSaveContainer())
        bgmPlayModel.stop()
    }

    override fun onDestroy() {
        Log.e(this.javaClass.simpleName, "onDestroy")
        dataSaveRepository.save(globalContainer.getDataSaveContainer())
    }

    override fun onPressAButton() {
        fieldViewModel.getPositionAndDirection {
            when(yagiFieldMap.getCheckEventTarget(it)) {
                EventTargetType.kuro -> kuro.check()
                EventTargetType.siro -> siro.check()
            }
        }
    }

    override fun onItemUsed(item: Item) {
        val gameItem = getGameItem(item.itemId)
        fieldViewModel.getPositionAndDirection {
            val isUsedItem: Boolean = when(yagiFieldMap.getCheckEventTarget(it)) {
                EventTargetType.kuro -> kuro.useItem(gameItem)
                EventTargetType.siro -> siro.useItem(gameItem)
                else -> false
            }
            if(!isUsedItem) {
                player.useItem(gameItem)
            }
        }
    }

    fun initFieldViewModel(fieldViewModel: FieldViewModel) {
        val position = globalContainer.lastPosition
        val field = yagiFieldMap.getField(position.fieldName)
        fieldViewModel.updateFieldAndGo(
                field,
                position.x,
                position.y
        )
        field.hasBGM?.let { globalContainer.bgmPlayModel.play(it) }

    }

    fun updatePositionAndDirection(positionAndDirection: PositionAndDirection) {
        val position = positionAndDirection.position
        globalContainer.lastPosition = position
        if(!isJump) {
            Log.e("SirokuroGame", "${position.fieldName.value}:${position.x.value}, ${position.y.value}, ${positionAndDirection.direction.name}")

            isJump = jump(
                    position,
                    fieldViewModel,
                    globalContainer.bgmPlayModel,
                    yagiFieldMap,
                    yagiFieldMap.linkList
            )

        } else {
            isJump = false
        }
    }

    override fun getItemList(): List<Item> {
        return globalContainer.itemSet.list
    }

}