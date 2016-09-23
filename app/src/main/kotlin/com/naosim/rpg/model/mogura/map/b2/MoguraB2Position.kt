package com.naosim.rpg.model.mogura.map.f2

import com.naosim.rpg.model.mogura.map.HasPosition
import com.naosim.rpg.model.mogura.map.MoguraFieldName
import com.naosim.rpglib.model.value.field.Position
import com.naosim.rpglib.model.value.field.X
import com.naosim.rpglib.model.value.field.Y

enum class MoguraB2Position(x: Int, y: Int): HasPosition {
    上り階段_in_house(1, 4),
    ネコ(1, 1),
    ;
    override val position = Position(MoguraFieldName.b2, X(x), Y(y))
}