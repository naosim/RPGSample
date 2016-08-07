package com.naosim.rpgmodel.lib

import com.naosim.rpgmodel.lib.value.Item

interface EventableObject<I:Item> {
    fun check()
    /**
     * return true if item was used.
     */
    fun useItem(item: I): Boolean {
        // nop default
        return false
    }
}