package com.naosim.rpglib.model

import com.naosim.rpglib.model.value.Item

interface EventableObject<I: Item> {
    fun check()
    /**
     * return true if item was used.
     */
    fun useItem(item: I): Boolean {
        // nop default
        return false
    }
}