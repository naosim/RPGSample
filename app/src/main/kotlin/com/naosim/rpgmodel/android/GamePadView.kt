package com.naosim.rpgmodel.android

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RelativeLayout
import com.naosim.rpgsample.R

class GamePadView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    val aButton: Button
    val bButton: Button
    val arrowPadView: ArrowPadView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_game_pad, this)
        aButton = findViewById(R.id.aButton) as Button
        bButton = findViewById(R.id.bButton) as Button
        arrowPadView = findViewById(R.id.arrowPadView) as ArrowPadView
    }
}