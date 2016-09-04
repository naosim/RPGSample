package com.naosim.rpg.model.mogura

import com.naosim.rpglib.model.value.ValueUpdater

class MoguraStatus(
        val b1Switch: B1Switch
)

class B1Switch(value: Boolean): ValueUpdater<Boolean, B1Switch>(value) {
    override fun getThis(): B1Switch {
        return this
    }

}