package com.naosim.rpg.model.mogura.map.f1

import com.naosim.rpg.model.mogura.map.HasPosition
import com.naosim.rpg.model.mogura.map.MoguraFieldName
import com.naosim.rpglib.model.value.field.Position
import com.naosim.rpglib.model.value.field.X
import com.naosim.rpglib.model.value.field.Y

enum class MoguraB1Position(x: Int, y: Int): HasPosition {
    上り階段(7, 6),
    スイッチ(4, 4);
    override val position = Position(MoguraFieldName.b1, X(x), Y(y))
}