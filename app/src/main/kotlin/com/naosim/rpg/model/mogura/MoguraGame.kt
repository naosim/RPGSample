package com.naosim.rpg.model.mogura

import android.util.Log
import com.naosim.rpg.model.mogura.map.GetFieldAndPosition
import com.naosim.rpg.model.mogura.map.HasPosition
import com.naosim.rpg.model.mogura.map.MoguraFieldMap
import com.naosim.rpg.model.mogura.map.MoguraMapEvent
import com.naosim.rpg.model.mogura.map.f1.MoguraB1Position
import com.naosim.rpg.model.mogura.map.f1.MoguraF1Position
import com.naosim.rpglib.model.GameMain
import com.naosim.rpglib.model.gametool.DataSaveRepository
import com.naosim.rpglib.model.gametool.GlobalCommonContainer
import com.naosim.rpglib.model.script.MessageScriptController
import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.field.PositionAndDirection
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModel
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModelFactory
import com.naosim.rpglib.model.viewmodel.sound.bgm.BGMPlayModel
import com.naosim.rpglib.model.viewmodel.sound.se.SEPlayModel


class MoguraGame(
        private val fieldViewModelFactory: FieldViewModelFactory<FieldViewModel>,
        private val messageScriptController: MessageScriptController,
        private val dataSaveRepository: DataSaveRepository<MoguraStatus, MoguraItem>,
        private val bgmPlayModel: BGMPlayModel,
        private val sePlayModel: SEPlayModel
): GameMain<FieldViewModel> {
    override val fieldViewModel: FieldViewModel
    var isJump = false;
    val globalContainer: MoguraGlobalContainer
    val moguraFieldMap: MoguraFieldMap
    val eventCallback: (MoguraMapEvent)->Unit = { handleEvent(it) }
    val player: MoguraPlayer
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
        this.globalContainer = MoguraGlobalContainer(
                dataSaveContainer.status,
                dataSaveContainer.itemSet,
                GlobalCommonContainer(
                        messageScriptController,
                        this.fieldViewModel,
                        dataSaveContainer.position,
                        bgmPlayModel,
                        sePlayModel
                )
        )
        this.player = MoguraPlayer(globalContainer)

        this.moguraFieldMap = MoguraFieldMap(globalContainer, eventCallback)

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
            moguraFieldMap.getFieldLogic(it.position.fieldName).check(it)
        }
    }

    override fun onItemUsed(item: Item) {
//        val gameItem = getGameItem(item.itemId)
        fieldViewModel.getPositionAndDirection {
            var isUsedItem: Boolean = false;
            fieldViewModel.getPositionAndDirection {
                isUsedItem = moguraFieldMap.getFieldLogic(it.position.fieldName).useItem(it, item)
            }
            if(!isUsedItem) {
                player.useItem(item)
            }
        }
    }

    fun initFieldViewModel(fieldViewModel: FieldViewModel) {
        val position = globalContainer.lastPosition
        val field = moguraFieldMap.getField(position.fieldName)
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
            isJump = moguraFieldMap.getFieldLogic(position.fieldName).onUpdatePositionAndDirection(positionAndDirection)
        } else {
            isJump = false
        }
    }

    override fun getItemList(): List<Item> {
        return globalContainer.itemSet.list
    }

    fun handleEvent(moguraMapEvent: MoguraMapEvent) {
        when(moguraMapEvent) {
            MoguraMapEvent.f1_move_to_b1 -> goto(moguraFieldMap.b1, MoguraB1Position.上り階段)
            MoguraMapEvent.b1_move_to_f1 -> goto(moguraFieldMap.f1, MoguraF1Position.下り階段)
        }
    }

    fun <T: HasPosition> goto(map: GetFieldAndPosition<T>, t: T) {
        fieldViewModel.updateFieldAndGo(map.getFieldAndPosition(t))
    }


}