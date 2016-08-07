package com.naosim.rpgmodel.lib.script

import com.naosim.rpgmodel.lib.value.Message

class ScriptUtil(val scriptExecutor: ScriptExecutor) {
    fun script(scriptSetBuilder: ScriptSetBuilder) {
        scriptExecutor.start(scriptSetBuilder.build())
    }

    fun script(kirikiriScript: String, onEnd: () -> Unit = {}) {
        scriptExecutor.start(ScriptSetBuilder().messageScriptList(ss(kirikiriScript)).onEnd(onEnd).build())
    }
}

fun s(text: String):SimpleMessageScript = SimpleMessageScript(Message(text))
fun ss(kirikiriScript: String): Array<MessageScript> {
    val b = kirikiriScript.trim()
    return kirikiriScript.trim().split("[r]")
            .map { it.split("\n").map { it.trim()}.joinToString("\n") }
            .map { Message(it) }
            .map{ SimpleMessageScript(it) }
            .toTypedArray()
}