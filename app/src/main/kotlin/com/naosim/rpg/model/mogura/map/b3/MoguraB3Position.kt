package com.naosim.rpg.model.mogura.map.f2

import com.naosim.rpg.model.mogura.map.HasPosition
import com.naosim.rpg.model.mogura.map.MoguraFieldName
import com.naosim.rpglib.model.value.field.Position
import com.naosim.rpglib.model.value.field.X
import com.naosim.rpglib.model.value.field.Y

enum class MoguraB3Position(x: Int, y: Int): HasPosition {
    上り階段_in_house(4, 1),
    下り階段_in_house(1, 4),
    ;
    override val position = Position(MoguraFieldName.b3, X(x), Y(y))
}