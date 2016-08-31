package com.naosim.rpglib.android.fieldviewmodel

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.naosim.rpglib.model.value.field.*
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModel
import org.json.JSONArray
import org.json.JSONObject

class WebFieldViewModelImpl(
        val webView: WebView,
        override val onload: (FieldViewModel) -> Unit,
        override val onstep: (FieldViewModel, PositionAndDirection) -> Unit
): FieldViewModel {
    init {
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onConsoleMessage(cm: ConsoleMessage): Boolean {
                if(cm.message().contains("###")) {
                    Log.e("WebFieldViewModelImpl", cm.message())
                    val args = cm.message().split("###")
                    val methodName = args[0]

                    if (methodName == "onload") {
                        onload.invoke(this@WebFieldViewModelImpl)
                    } else if (methodName == "position") {
                        val obj = JSONObject(args[1])
                        val position = Position(
                                FieldNameImpl(obj.getString("fieldName")),
                                X(obj.getInt("x")),
                                Y(obj.getInt("y"))
                        )
                        onstep.invoke(this@WebFieldViewModelImpl, PositionAndDirection(
                                position,
                                createDirection(obj.getString("direction"))
                        ))
                    }
                    return true
                } else {
                    Log.e("console${cm.lineNumber()}", cm.message())
                }

                return false
            }
        })
    }


    override fun getPositionAndDirection(callback: (PositionAndDirection) -> Unit) {
        webView.evaluateJavascript("fromNative.getPosition()") {
            val v = JSONObject(it)
            val position = Position(
                    FieldNameImpl(v.getString("fieldName")!!),
                    X(v.getInt("x")),
                    Y(v.getInt("y"))
            )
            callback.invoke(
                    PositionAndDirection(
                            position,
                            createDirection(v.getString("direction"))
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
        json.put("fieldCollisionData", if(fieldLayer.fieldCollisionData != null) JSONArray(fieldLayer.fieldCollisionData.value) else null)
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

    /*
    override fun call(input: I, callback: (I) -> Unit) {
        modelConverter?.let {
            val inputJSON = it.encode(input)
            webView.evaluateJavascript("fromNative.call(${inputJSON})"){
                it?.let {
                    val json = JSONObject(it)
                    callback(it@modelConverter.decode(json))
                }

            }
        }
    }
    */
}

