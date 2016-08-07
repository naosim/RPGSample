package com.naosim.rpgmodel.lib.viewmodel

import com.naosim.rpgmodel.lib.value.Message

interface MessageViewModel {
    fun clear()
    fun appendText(message: Message, onEnd: () -> Unit)
    fun show()
    fun dismiss()
    fun isShowing(): Boolean
}