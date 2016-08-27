package com.naosim.rpglib.model.viewmodel.sound.bgm

import com.naosim.rpglib.model.viewmodel.sound.bgm.HasBGM

interface BGMPlayModel {
    fun play(hasBGM: HasBGM)
    fun restart()
    fun stop()
}