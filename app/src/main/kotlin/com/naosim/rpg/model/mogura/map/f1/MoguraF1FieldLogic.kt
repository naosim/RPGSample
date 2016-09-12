package com.naosim.rpg.model.mogura.map.f1

import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.MoguraItem
import com.naosim.rpg.model.mogura.ScriptText
import com.naosim.rpg.model.mogura.map.FieldAndFieldLogic
import com.naosim.rpg.model.mogura.map.GetFieldAndPosition
import com.naosim.rpg.model.mogura.map.MapTip
import com.naosim.rpg.model.mogura.map.MoguraMapEvent
import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.field.PositionAndDirection
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldAndPosition

class MoguraF1FieldLogic(
        val globalContainer: MoguraGlobalContainer,
        val moguraF1FieldFactory: MoguraF1FieldFactory,
        val eventCallback: (MoguraMapEvent)->Unit
): FieldAndFieldLogic<Item>, GetFieldAndPosition<MoguraF1Position> {
    override var field = moguraF1FieldFactory.createField()

    override fun onUpdatePositionAndDirection(positionAndDirection: PositionAndDirection): Boolean {
        val fieldValue = field.getUpperFieldValue(positionAndDirection.position)
        if(MapTip.下り階段.eq(fieldValue)) {
            eventCallback.invoke(MoguraMapEvent.f1_move_to_b1)
            return true
        }
        return false
    }

    override fun check(positionAndDirection: PositionAndDirection) {
        val fieldValue = field.getUpperFieldValue(positionAndDirection.getFrontPosition())

        if(MapTip.テーブル.eq(fieldValue)) {
            if(globalContainer.notHaveItem(MoguraItem.父親のメモ)) {
                globalContainer.scriptUtil.script(ScriptText.f1_table_find_letter) { globalContainer.itemSet.add(MoguraItem.父親のメモ) }
            } else {
                globalContainer.scriptUtil.script(ScriptText.f1_table_nothing)
            }
        } else if(MapTip.看板.eq(fieldValue)) {
            globalContainer.scriptUtil.script(ScriptText.f1_看板)
        }
    }

    override fun useItem(positionAndDirection: PositionAndDirection, item: Item): Boolean {
        return super.useItem(positionAndDirection, item)
    }

    override fun getFieldAndPosition(moguraF1Position: MoguraF1Position): FieldAndPosition {
        return FieldAndPosition(field, moguraF1Position.position.x, moguraF1Position.position.y)
    }

}

