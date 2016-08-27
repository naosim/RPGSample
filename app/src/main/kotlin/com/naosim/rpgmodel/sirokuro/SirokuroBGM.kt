package com.naosim.rpgmodel.sirokuro

import com.naosim.rpgmodel.lib.model.viewmodel.BGM
import com.naosim.rpgmodel.lib.model.viewmodel.HasBGM

enum class SirokuroBGM(bgmName: String): HasBGM {
    bgm1("game_maoudamashii_4_field03.mp3"),
    bgm2("game_maoudamashii_4_field04.mp3");
    override val bgm: BGM = BGM(bgmName)

}