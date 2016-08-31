package com.naosim.rpglib.android.fieldviewmodel

import org.json.JSONObject

open public class EmptyModelConverter<A>: ModelConverter<A, JSONObject> {
    override fun encode(a: A): JSONObject {
        return JSONObject()
    }

    override fun decode(b: JSONObject): A {
        return null!!
    }

}