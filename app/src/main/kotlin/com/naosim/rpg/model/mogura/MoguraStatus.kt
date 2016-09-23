package com.naosim.rpg.model.mogura

import com.naosim.rpglib.model.value.ValueUpdater

class MoguraStatus(
        val b1Switch: B1Switch,
        val earthquake: Earthquake
) {
    fun invokeAll() {
        b1Switch.invoke()
    }
}

class B1Switch(value: Boolean): ValueUpdater<Boolean, B1Switch>(value) {
    override fun getThis(): B1Switch {
        return this
    }

}

class Earthquake(value: Boolean): ValueUpdater<Boolean, Earthquake>(value) {
    override fun getThis(): Earthquake {
        return this
    }

}