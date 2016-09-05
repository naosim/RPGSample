package com.naosim.rpglib.model.value.field

import com.naosim.rpglib.model.value.ValueImutable
import java.util.*

class FieldData(override val value: List<MutableList<Int>>) : ValueImutable<List<List<Int>>> {
    fun getPositionValue(x: X, y: Y): FieldValue {
        return FieldValue(value[y.value][x.value])
    }

    fun set(x: X, y: Y, fieldValue: FieldValue) {
        value[y.value].set(x.value, fieldValue.value)
    }
}
class FieldCollisionData(override val value: List<MutableList<Int>>) : ValueImutable<List<List<Int>>> {
    fun getPositionValue(x: X, y: Y): Int {
        return value[y.value][x.value]
    }

    fun set(x: X, y: Y, value: Int) {
        this.value[y.value].set(x.value, value)
    }
}
fun createFieldData(data: String): FieldData {
    val result = ArrayList<ArrayList<Int>>()
    data
            .trim()
            .split("\n")
            .map({ it.trim() })
            .forEach({
                val l = ArrayList<Int>()
                it.split(",").map({it.trim().toInt()}).forEach { l.add(it) }
                result.add(l)
            })
    return FieldData(result)
}

fun createFieldCollisionData(data: String): FieldCollisionData {
    val result = ArrayList<ArrayList<Int>>()
    data
            .trim()
            .split("\n")
            .map({ it.trim() })
            .forEach({
                val l = ArrayList<Int>()
                it.split(",").map( {it.toInt()} ).forEach { l.add(it) }
                result.add(l)
            })
    return FieldCollisionData(result)
}