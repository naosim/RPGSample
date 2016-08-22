package com.naosim.rpgmodel.lib.model

import com.naosim.rpgmodel.lib.model.value.Item

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