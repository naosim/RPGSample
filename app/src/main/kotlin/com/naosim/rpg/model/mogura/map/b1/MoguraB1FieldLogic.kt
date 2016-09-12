package com.naosim.rpg.model.mogura.map.b1

import com.naosim.rpg.model.mogura.MoguraGlobalContainer
import com.naosim.rpg.model.mogura.ScriptText
import com.naosim.rpg.model.mogura.map.FieldAndFieldLogic
import com.naosim.rpg.model.mogura.map.GetFieldAndPosition
import com.naosim.rpg.model.mogura.map.MapTip
import com.naosim.rpg.model.mogura.map.MoguraMapEvent
import com.naosim.rpg.model.mogura.map.f1.MoguraB1Position
import com.naosim.rpglib.model.value.Item
import com.naosim.rpglib.model.value.field.PositionAndDirection
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldAndPosition

class MoguraB1FieldLogic(
        val globalContainer: MoguraGlobalContainer,
        val moguraB1FieldFactory: MoguraB1FieldFactory,
        val eventCallback: (MoguraMapEvent)->Unit
): FieldAndFieldLogic<Item>, GetFieldAndPosition<MoguraB1Position> {
    val scriptUtil = globalContainer.scriptUtil
    override var field = moguraB1FieldFactory.createField()

    init {
        globalContainer.status.b1Switch.registerUpdate(this, {
            // スイッチが置かれたら道を開く
            updateField()
//            if(it.getValue()) {
//                updateField()
//                // 2, 7
//                field.frontFieldLayer?.let { it.fieldDataAndFieldCollisionData.set(X(2), Y(7), FieldValue(-1), 0) }
//                globalContainer.fieldViewModel.updateField(field)
//            }
//            Log.e(this@MoguraB1FieldLogic.javaClass.simpleName, it.getValue().toString())
        })
    }

    private fun updateField() {
        field = moguraB1FieldFactory.createField()
        globalContainer.fieldViewModel.updateField(field)
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
            scriptUtil.script(ScriptText.b1_switch_before) {
                globalContainer.status.b1Switch.setValue(true)
                scriptUtil.script(ScriptText.b1_switch_after)
            }


        }
    }

    override fun useItem(positionAndDirection: PositionAndDirection, item: Item): Boolean {
        return super.useItem(positionAndDirection, item)
    }

    override fun getFieldAndPosition(moguraB1Position: MoguraB1Position): FieldAndPosition {
        return FieldAndPosition(field, moguraB1Position.position.x, moguraB1Position.position.y)
    }

}