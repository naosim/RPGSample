package com.naosim.rpgmodel.lib.script

import com.naosim.rpgmodel.lib.viewmodel.MessageViewModel
import com.naosim.rpgmodel.lib.viewmodel.OnNextListener

class MessageScriptLooper(
        val messageViewModel: MessageViewModel,
        val scriptSet: ScriptSet
): OnNextListener {
    var count = 0;

    fun start() {
        next()
    }

    fun next() {
        if(count >= scriptSet.messageScriptList.size) {
            messageViewModel.dismiss()
            scriptSet.onEnd.invoke()
            return
        }
        val script = scriptSet.messageScriptList.get(count)
        if(script is SimpleMessageScript) {
            messageViewModel.clear()
            messageViewModel.appendText(script.message, { waitNext() })
        }

        count++
    }

    var isWaitingNext = false;
    fun waitNext() {
        isWaitingNext = true;
    }
    override fun onNext() {
        if(!isWaitingNext) {
            return
        }
        next()
        isWaitingNext = false
    }
}