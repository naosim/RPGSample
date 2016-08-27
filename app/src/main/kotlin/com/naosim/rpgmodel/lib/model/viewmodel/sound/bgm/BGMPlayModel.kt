package com.naosim.rpgmodel.lib.model.viewmodel.sound.bgm

import com.naosim.rpgmodel.lib.model.viewmodel.sound.bgm.HasBGM

interface BGMPlayModel {
    fun play(hasBGM: HasBGM)
    fun restart()
    fun stop()
}