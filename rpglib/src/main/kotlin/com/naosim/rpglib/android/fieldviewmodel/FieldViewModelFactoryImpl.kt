package com.naosim.rpglib.android.fieldviewmodel

import android.webkit.WebView
import com.naosim.rpglib.model.value.field.PositionAndDirection
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModel
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModelFactory

class FieldViewModelFactoryImpl(val webView: WebView): FieldViewModelFactory {
    override fun create(
            onload: (FieldViewModel) -> Unit,
            onstep: (FieldViewModel, PositionAndDirection) -> Unit
    ): FieldViewModel {
        return WebFieldViewModelImpl(webView, onload, onstep)
    }
}