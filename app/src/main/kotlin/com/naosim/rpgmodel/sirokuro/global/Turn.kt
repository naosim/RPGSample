package com.naosim.rpgmodel.sirokuro.global

import com.naosim.rpglib.model.value.Value
import com.naosim.rpglib.model.value.ValueUpdater

enum class Turn {
    kuro_eat, kuro_write, siro_eat, siro_write;
    val value = name
}

interface TurnValue: Value<Turn, TurnValue> {
}
class TurnValueUpdater(turn: Turn = Turn.kuro_eat) : ValueUpdater<Turn, TurnValue>(turn), TurnValue {
    fun next() {
        setValue(Turn.values()[(getValue().ordinal + 1) % Turn.values().size])
    }

    override fun invoke() {
        registerManager.invoke(this)
    }

    fun getValueString(): String {
        return getValue().value
    }
}