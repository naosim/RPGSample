package com.naosim.rpg.model.mogura.map

import com.naosim.rpg.model.mogura.map.f1.MoguraB1Position
import com.naosim.rpg.model.mogura.map.f1.MoguraF1Position
import com.naosim.rpg.model.mogura.map.f2.MoguraB2Position
import com.naosim.rpg.model.mogura.map.f2.MoguraB3Position

enum class MoguraMapMoveEvent(hasPosition: HasPosition): HasFieldNameAndHasPosition {
    f1_move_to_b1(MoguraB1Position.上り階段),
    b1_move_to_f1(MoguraF1Position.下り階段),
    b1_move_to_b2(MoguraB2Position.上り階段_in_house),
    b2_move_to_b1_in_house(MoguraB1Position.下り階段),
    b2_move_to_b3_in_house(MoguraB3Position.上り階段_in_house),
    b2_move_to_b1_outof_house(MoguraB1Position.下り階段),
    ;
    override val fieldName: MoguraFieldName
    override val position = hasPosition.position

    init {
        fieldName =  MoguraFieldName.values().filter { name.contains("to_${it.value}")}.first()
    }


}