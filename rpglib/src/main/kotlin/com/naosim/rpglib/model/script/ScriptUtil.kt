package com.naosim.rpglib.model.script

import com.naosim.rpglib.model.value.Message
import com.naosim.rpglib.model.value.ValueImutable

class ScriptUtil(val scriptExecutor: ScriptExecutor) {
    fun script(scriptSetBuilder: ScriptSetBuilder) {
        scriptExecutor.start(scriptSetBuilder.build())
    }

    fun script(kirikiriScript: String, onEnd: () -> Unit = {}) {
        scriptExecutor.start(ScriptSetBuilder().messageScriptList(ss(kirikiriScript)).onEnd(onEnd).build())
    }

    fun script(hasKirikiriScript: HasKirikiriScript, onEnd: () -> Unit = {}) {
        scriptExecutor.start(ScriptSetBuilder().messageScriptList(ss(hasKirikiriScript.kirikiriScript.value)).onEnd(onEnd).build())
    }
}

fun s(text: String): SimpleMessageScript = SimpleMessageScript(Message(text))
fun ss(kirikiriScript: String): Array<MessageScript> {
    val b = kirikiriScript.trim()
    return kirikiriScript.trim().split("[r]")
            .map { it.split("\n").map { it.trim()}.joinToString("\n") }
            .map { Message(it) }
            .map{ SimpleMessageScript(it) }
            .toTypedArray()
}

class KirikiriScript(override val value: String) : ValueImutable<String>
interface HasKirikiriScript {
    val kirikiriScript: KirikiriScript
}