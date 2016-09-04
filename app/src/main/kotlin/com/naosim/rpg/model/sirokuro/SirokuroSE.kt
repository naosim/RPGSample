package com.naosim.rpg.model.sirokuro

import com.naosim.rpglib.model.viewmodel.sound.se.HasSE
import com.naosim.rpglib.model.viewmodel.sound.se.SE

enum class SirokuroSE(seName: String): HasSE {
    se1("se_maoudamashii_retro22.mp3");
    override val se: SE = SE(seName)

}