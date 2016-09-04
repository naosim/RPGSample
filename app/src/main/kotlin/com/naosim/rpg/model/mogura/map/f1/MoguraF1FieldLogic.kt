package com.naosim.rpg.model.mogura.map.f1

import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.map.FieldAndFieldLogic
import com.naosim.rpg.model.mogura.map.GetFieldAndPosition
import com.naosim.rpg.model.mogura.map.MapTip
import com.naosim.rpg.model.mogura.map.MoguraMapEvent
import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.field.Field
import com.naosim.rpglib.model.value.field.PositionAndDirection
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldAndPosition

class MoguraF1FieldLogic(
        val glovalContainer: MoguraGlobalContainer,
        override val field: Field,
        val eventCallback: (MoguraMapEvent)->Unit
): FieldAndFieldLogic<Item>, GetFieldAndPosition<MoguraF1Position> {
    override fun onUpdatePositionAndDirection(positionAndDirection: PositionAndDirection): Boolean {
        val fieldValue = field.getUpperFieldValue(positionAndDirection.position)
        if(MapTip.下り階段.eq(fieldValue)) {
            eventCallback.invoke(MoguraMapEvent.f1_move_to_b1)
            return true
        }
        return false
    }

    override fun check(positionAndDirection: PositionAndDirection) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun useItem(positionAndDirection: PositionAndDirection, item: Item): Boolean {
        return super.useItem(positionAndDirection, item)
    }

    override fun getFieldAndPosition(moguraF1Position: MoguraF1Position): FieldAndPosition {
        return FieldAndPosition(field, moguraF1Position.position.x, moguraF1Position.position.y)
    }

}

