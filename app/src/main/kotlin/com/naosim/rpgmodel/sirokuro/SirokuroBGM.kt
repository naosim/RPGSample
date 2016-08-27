package com.naosim.rpgmodel.sirokuro

import com.naosim.rpglib.model.viewmodel.sound.bgm.BGM
import com.naosim.rpglib.model.viewmodel.sound.bgm.HasBGM

enum class SirokuroBGM(bgmName: String): HasBGM {
    bgm1("game_maoudamashii_4_field03.mp3"),
    bgm2("game_maoudamashii_4_field04.mp3");
    override val bgm: BGM = BGM(bgmName)

}