package com.naosim.rpg.model.mogura.map.b1

import android.util.Log
import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.map.FieldAndFieldLogic
import com.naosim.rpg.model.mogura.map.GetFieldAndPosition
import com.naosim.rpg.model.mogura.map.MapTip
import com.naosim.rpg.model.mogura.map.MoguraMapEvent
import com.naosim.rpg.model.mogura.map.f1.MoguraB1Position
import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.field.*
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldAndPosition

class MoguraB1FieldLogic(
        val glovalContainer: MoguraGlobalContainer,
        override var field: Field,
        val eventCallback: (MoguraMapEvent)->Unit
): FieldAndFieldLogic<Item>, GetFieldAndPosition<MoguraB1Position> {
    init {
        glovalContainer.status.b1Switch.registerUpdate(this, {
            Log.e(this@MoguraB1FieldLogic.javaClass.simpleName, it.getValue().toString())
        })
    }

    override fun onUpdatePositionAndDirection(positionAndDirection: PositionAndDirection): Boolean {
        val fieldValue = field.getUpperFieldValue(positionAndDirection.position)
        if(MapTip.上り階段.eq(fieldValue)) {
            eventCallback.invoke(MoguraMapEvent.b1_move_to_f1)
            return true
        }
        return false
    }

    override fun check(positionAndDirection: PositionAndDirection) {
        if(MoguraB1Position.スイッチ.position.isSame(positionAndDirection.position)) {
            eventCallback.invoke(MoguraMapEvent.b1_switch)
        }
    }

    override fun useItem(positionAndDirection: PositionAndDirection, item: Item): Boolean {
        return super.useItem(positionAndDirection, item)
    }

    override fun getFieldAndPosition(moguraB1Position: MoguraB1Position): FieldAndPosition {
        return FieldAndPosition(field, moguraB1Position.position.x, moguraB1Position.position.y)
    }

}