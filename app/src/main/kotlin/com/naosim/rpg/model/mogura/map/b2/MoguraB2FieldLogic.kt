package com.naosim.rpg.model.mogura.map.b2

import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.ScriptText
import com.naosim.rpg.model.mogura.map.FieldAndFieldLogic
import com.naosim.rpg.model.mogura.map.GetFieldAndPosition
import com.naosim.rpg.model.mogura.map.MapTip
import com.naosim.rpg.model.mogura.map.MoguraMapMoveEvent
import com.naosim.rpg.model.mogura.map.f2.MoguraB2Position
import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.field.PositionAndDirection
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldAndPosition

class MoguraB2FieldLogic(
        val globalContainer: MoguraGlobalContainer,
        val fieldFactory: MoguraB2FieldFactory,
        val eventCallback: (MoguraMapMoveEvent)->Unit
): FieldAndFieldLogic<Item>, GetFieldAndPosition<MoguraB2Position> {
    val scriptUtil = globalContainer.scriptUtil
    override var field = fieldFactory.createField()

    init {
        globalContainer.status.earthquake.registerUpdate(this, {
            updateField()
        })
    }

    private fun updateField() {
        field = fieldFactory.createField()
        globalContainer.fieldViewModel.updateField(field)
    }

    override fun onUpdatePositionAndDirection(positionAndDirection: PositionAndDirection): Boolean {
        val fieldValue = field.getUpperFieldValue(positionAndDirection.position)
        if(MapTip.上り階段.eq(fieldValue)) {
            eventCallback.invoke(MoguraMapMoveEvent.b2_move_to_b1_in_house)
            return true
        }
        if(MapTip.下り階段.eq(fieldValue)) {
            eventCallback.invoke(MoguraMapMoveEvent.b2_move_to_b3_in_house)
            return true
        }
        return false
    }

    override fun check(positionAndDirection: PositionAndDirection) {
        if(MoguraB2Position.ネコ.position.isSame(positionAndDirection.getFrontPosition())) {
            scriptUtil.script(ScriptText.b2_neko_before_eathquake)
        }
//        if(MoguraB1Position.スイッチ.position.isSame(positionAndDirection.position)) {
//            scriptUtil.script(ScriptText.b1_switch_before) {
//                globalContainer.status.b1Switch.setValue(true)
//                scriptUtil.script(ScriptText.b1_switch_after)
//            }
//
//
//        }
    }

    override fun useItem(positionAndDirection: PositionAndDirection, item: Item): Boolean {
        return super.useItem(positionAndDirection, item)
    }

    override fun getFieldAndPosition(moguraB2Position: MoguraB2Position): FieldAndPosition {
        return FieldAndPosition(field, moguraB2Position.position.x, moguraB2Position.position.y)
    }

}