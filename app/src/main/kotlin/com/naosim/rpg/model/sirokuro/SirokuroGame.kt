package com.naosim.rpg.model.sirokuro

import android.util.Log
import com.naosim.rpglib.model.GameMain
import com.naosim.rpglib.model.script.MessageScriptController
import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.field.PositionAndDirection
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModel
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModelFactory
import com.naosim.rpglib.model.viewmodel.sound.bgm.BGMPlayModel
import com.naosim.rpglib.model.viewmodel.sound.se.SEPlayModel
import com.naosim.rpg.model.sirokuro.charactor.*
import com.naosim.rpglib.model.gametool.DataSaveRepository
import com.naosim.rpglib.model.gametool.GlobalCommonContainer
import com.naosim.rpglib.model.gametool.GlobalContainer
import com.naosim.rpg.model.sirokuro.global.Status
import com.naosim.rpg.model.sirokuro.map.YagiFieldMap
import com.naosim.rpg.model.sirokuro.map.jump

class SirokuroGame(
        private val fieldViewModelFactory: FieldViewModelFactory<FieldViewModel>,
        private val messageScriptController: MessageScriptController,
        private val dataSaveRepository: DataSaveRepository<Status, GameItem>,
        private val bgmPlayModel: BGMPlayModel,
        private val sePlayModel: SEPlayModel
): GameMain<FieldViewModel> {
    override val fieldViewModel: FieldViewModel
    val kuro: KuroYagi
    val siro: SiroYagi
    val player: Player
    val yagiFieldMap = YagiFieldMap()

    var isJump = false;

    val globalContainer: GlobalContainer<Status, GameItem>

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
        val globalCommonContainer = GlobalCommonContainer(
                messageScriptController,
                this.fieldViewModel,
                dataSaveContainer.position,
                bgmPlayModel,
                sePlayModel
        )
        this.globalContainer = GlobalContainer(
                dataSaveContainer.status,
                dataSaveContainer.itemSet,
                globalCommonContainer
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
                    globalContainer.sePlayModel,
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