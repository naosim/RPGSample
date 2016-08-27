package com.naosim.rpgmodel.lib.model.viewmodel

import com.naosim.rpgmodel.lib.model.value.ValueImutable

interface SoundPlayModel {
    fun playBGM(hasBGM: HasBGM)
    fun playSE(hasSE: HasSE, callback: () -> Unit = {})
}

interface BGMSoundPlayModel {
    fun play(hasBGM: HasBGM)
    fun restart()
    fun stop()
    fun setIsOn(isOn: Boolean)
    fun isOn(): Boolean
}

interface SEPlayModel {
    fun play(hasSE: HasSE)
    fun setIsOn(isOn: Boolean)
    fun isOn(): Boolean
}

class BGM(override val value: String) : ValueImutable<String>
class SE(override val value: String) : ValueImutable<String>
interface HasBGM {
    val bgm: BGM
}
interface HasSE {
    val se: SE
}