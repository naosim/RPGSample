package com.naosim.rpg.model.mogura.map.b2

import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.map.FieldAndFieldLogic
import com.naosim.rpg.model.mogura.map.GetFieldAndPosition
import com.naosim.rpg.model.mogura.map.MoguraMapMoveEvent
import com.naosim.rpg.model.mogura.map.f2.MoguraB3Position
import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.field.PositionAndDirection
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldAndPosition

class MoguraB3FieldLogic(
        val globalContainer: MoguraGlobalContainer,
        val fieldFactory: MoguraB3FieldFactory,
        val eventCallback: (MoguraMapMoveEvent)->Unit
): FieldAndFieldLogic<Item>, GetFieldAndPosition<MoguraB3Position> {
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
//        if(MapTip.上り階段.eq(fieldValue)) {
//            eventCallback.invoke(MoguraMapEvent.b2_move_to_b1_in_house)
//            return true
//        }

//        if(MapTip.下り階段.eq(fieldValue)) {
//            eventCallback.invoke(MoguraMapEvent.b2_move_to_b1_in_house)
//            return true
//        }
        return false
    }

    override fun check(positionAndDirection: PositionAndDirection) {

    }

    override fun useItem(positionAndDirection: PositionAndDirection, item: Item): Boolean {
        return super.useItem(positionAndDirection, item)
    }

    override fun getFieldAndPosition(moguraB3Position: MoguraB3Position): FieldAndPosition {
        return FieldAndPosition(field, moguraB3Position.position.x, moguraB3Position.position.y)
    }

}