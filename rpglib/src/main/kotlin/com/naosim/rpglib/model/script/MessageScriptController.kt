package com.naosim.rpglib.model.script

import com.naosim.rpglib.model.viewmodel.MessageViewModel
import com.naosim.rpglib.model.viewmodel.OnNextListener

class MessageScriptController(val messageViewModel: MessageViewModel): OnNextListener, ScriptExecutor {
    var onNextListener: OnNextListener? = null

    override fun start(scriptSet: ScriptSet) {
        if(messageViewModel.isShowing()) {
            return
        }

        val l = MessageScriptLooper(
                messageViewModel,
                scriptSet
        )
        onNextListener = l
        messageViewModel.show()
        l.start()
    }

    override fun onNext() {
        onNextListener?.onNext()
    }
}


