package com.naosim.rpg.model.mogura.map.f1

import com.naosim.rpg.model.mogura.map.HasPosition
import com.naosim.rpg.model.mogura.map.MoguraFieldName
import com.naosim.rpglib.model.value.field.Position
import com.naosim.rpglib.model.value.field.X
import com.naosim.rpglib.model.value.field.Y

enum class MoguraF1Position(x: Int, y: Int): HasPosition {
    下り階段(7, 11);
    override val position = Position(MoguraFieldName.f1, X(x), Y(y))
}