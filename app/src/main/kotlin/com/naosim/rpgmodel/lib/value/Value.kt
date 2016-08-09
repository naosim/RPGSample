package com.naosim.rpgmodel.lib.value

interface ValueImutable<T> {
    val value: T
}

interface Value<T, THIZ> {
    fun getValue(): T
    fun registerUpdate(thiz: Any, callback: (THIZ) -> Unit)
    fun unregisterUpdate(thiz: Any)
}

open abstract class ValueUpdater<T, THIZ>(private var v: T): Value<T, THIZ> {
    protected val registerManager = RegisterManager<THIZ>()
    override fun registerUpdate(thiz: Any, callback: (THIZ) -> Unit) {
        registerManager.register(thiz, callback)
    }

    override fun unregisterUpdate(thiz: Any) {
        registerManager.unregister(thiz)
    }

    override fun getValue(): T {
        return v
    }

    fun setValue(v: T) {
        this.v = v
    }

    abstract fun invoke()
}
interface CountValue: Value<Int, CountValue>
class CountValueUpdater(v: Int): ValueUpdater<Int, CountValue>(v), CountValue {

    fun increment(countUpValue: Int = 1) {
        setValue(getValue() + countUpValue)
    }

    fun decrement(countDownValue: Int = 1) {
        setValue(getValue() + Math.abs(countDownValue))
    }

    override fun invoke() {
        registerManager.invoke(this)
    }
}