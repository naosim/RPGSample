package com.naosim.rpgmodel.lib.viewmodel

import com.naosim.rpgmodel.lib.value.ValueImutable

interface SoundPlayModel {
    fun playBGM(hasBGM: HasBGM)
    fun playSE(hasSE: HasSE, callback: () -> Unit = {})
}

class BGM(override val value: Long) : ValueImutable<Long>
class SE(override val value: Long) : ValueImutable<Long>
interface HasBGM {
    val bgm: BGM
}
interface HasSE {
    val se: SE
}