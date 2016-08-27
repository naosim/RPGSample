package com.naosim.rpglib.model.viewmodel

import com.naosim.rpglib.model.value.Message

interface MessageViewModel {
    fun clear()
    fun appendText(message: Message, onEnd: () -> Unit)
    fun show()
    fun dismiss()
    fun isShowing(): Boolean
}