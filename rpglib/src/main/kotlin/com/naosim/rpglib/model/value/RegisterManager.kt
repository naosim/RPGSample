package com.naosim.rpglib.model.value

import java.util.*

interface RegisterManagerPublic<T> {
    fun register(thiz: Any, callback: (T) -> Unit)
    fun unregister(thiz: Any)
}

class RegisterManager<T>: RegisterManagerPublic<T> {
    val callbackMap = HashMap<Any, (T) -> Unit>()

    override fun register(thiz: Any, callback: (T) -> Unit) {
        callbackMap.put(thiz, callback)
    }

    override fun unregister(thiz: Any) {
        callbackMap.remove(thiz)
    }

    fun invoke(t: T) {
        callbackMap.forEach { it.value.invoke(t) }
    }
}