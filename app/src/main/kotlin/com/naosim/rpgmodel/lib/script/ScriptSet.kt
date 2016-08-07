package com.naosim.rpgmodel.lib.script

class ScriptSet(val messageScriptList: Array<MessageScript>, val onEnd: () -> Unit)
class ScriptSetBuilder {
    var messageScriptList: Array<MessageScript> = emptyArray()
    private set

    private var onEnd :() -> Unit = {}
    private set

    fun messageScript(vararg messageScriptList: MessageScript): ScriptSetBuilder {
        this.messageScriptList = this.messageScriptList.plus(messageScriptList)
        return this
    }

    fun messageScriptList(messageScriptList: Array<MessageScript>): ScriptSetBuilder {
        this.messageScriptList = this.messageScriptList.plus(messageScriptList)
        return this
    }

    fun onEnd(onEnd: () -> Unit): ScriptSetBuilder {
        this.onEnd = onEnd
        return this
    }

    fun build(): ScriptSet {
        return ScriptSet(messageScriptList, onEnd)
    }
}