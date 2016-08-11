package com.naosim.rpgmodel.android

import android.util.Log
import android.webkit.WebView
import com.naosim.rpgmodel.lib.viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class WebFieldViewModelImpl(val webView: WebView): FieldViewModel {
    override fun getPosition(callback: (Position) -> Unit) {
        val v = HashMap<String, String>()
        webView.evaluateJavascript("fromNative.getPosition()") {
            val args = it.replace("\"", "").split(",")
            v.put("fieldName", args[0])
            v.put("x", args[1])
            v.put("y", args[2])

            callback.invoke(
                    Position(
                            FieldName(v.get("fieldName")!!),
                            X(v.get("x")!!.toInt()),
                            Y(v.get("y")!!.toInt())
                    )
            )
        }
    }

    override fun gotoPosition(pos: Position) {
        val json = JSONObject()
        json.put("fieldName", pos.fieldName.value)
        json.put("x", pos.x.value)
        json.put("y", pos.y.value)
        Log.e("WebFieldViewModelImpl", json.toString())
        webView.evaluateJavascript("fromNative.gotoPosition(${json.toString()})"){}
    }

    override fun updateFieldLayer(fieldLayer: FieldLayer) {
        val json = JSONObject()
        json.put("fieldName", fieldLayer.fieldName.value)
        json.put("fieldLayerName", fieldLayer.fieldLayerName.value)
        json.put("fieldData", JSONArray(fieldLayer.fieldData.value))
        Log.e("WebFieldViewModelImpl", json.toString())
        webView.evaluateJavascript("fromNative.updateFieldLayer(${json.toString()})"){}
    }

    override fun runFieldEffect(fieldEffect: FieldEffect, callback: () -> Unit) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onButtonDown(arrowButtonType: ArrowButtonType) {
        webView.evaluateJavascript("fromNative.onButtonDown(\"${arrowButtonType.name}\")"){}
        Log.e("WebFieldViewModelImpl", "onButtonDown " + arrowButtonType.name)
    }

    override fun onButtonUp(arrowButtonType: ArrowButtonType) {
        webView.evaluateJavascript("fromNative.onButtonUp(\"${arrowButtonType.name}\")"){}
        Log.e("WebFieldViewModelImpl", "onButtonUp " + arrowButtonType.name)
    }
}