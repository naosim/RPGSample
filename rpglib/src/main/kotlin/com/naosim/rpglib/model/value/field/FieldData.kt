package com.naosim.rpglib.model.value.field

import com.naosim.rpglib.model.value.ValueImutable
import java.util.*

class FieldData(override val value: List<List<Int>>) : ValueImutable<List<List<Int>>> {
    fun getPositionValue(x: X, y: Y): FieldValue {
        return FieldValue(value[y.value][x.value])
    }
}
class FieldCollisionData(override val value: List<List<Int>>) : ValueImutable<List<List<Int>>> {
    fun getPositionValue(x: X, y: Y): Int {
        return value[y.value][x.value]
    }
}
fun createFieldData(data: String): FieldData {
    val result = ArrayList<List<Int>>()
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
    val result = ArrayList<List<Int>>()
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