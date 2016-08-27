package com.naosim.rpgmodel.lib.android

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import com.naosim.rpgmodel.lib.model.viewmodel.BGMSoundPlayModel
import com.naosim.rpgmodel.lib.model.viewmodel.HasBGM

class BGMSoundPlayModelImpl (
        val context: Context,
        val bgmSoundPlayModelOnFactory: (BGMSoundPlayModelContext) -> BGMSoundPlayModelOn,
        val bgmSoundPlayModelOffFactory: (BGMSoundPlayModelContext) -> BGMSoundPlayModelOff
): BGMSoundPlayModel, SoundPlayModelImplStateUpdater {
    var bgmSoundPlayModel: BGMSoundPlayModel = BGMSoundPlayModelOff(BGMSoundPlayModelContext(context, this))
    constructor(context: Context) :this(
            context,
            { BGMSoundPlayModelOn(it) },
            { BGMSoundPlayModelOff(it) }
    )

    override fun updateBGMSoundPlayModel(soundPlayModelState: SoundPlayModelState, bgmSoundPlayModelContext: BGMSoundPlayModelContext) {
        Toast.makeText(context, soundPlayModelState.name, Toast.LENGTH_SHORT).show()
        this.bgmSoundPlayModel = when(soundPlayModelState) {
            SoundPlayModelState.on -> bgmSoundPlayModelOnFactory.invoke(bgmSoundPlayModelContext)
            SoundPlayModelState.off -> bgmSoundPlayModelOffFactory.invoke(bgmSoundPlayModelContext)
        }
    }

    override fun play(hasBGM: HasBGM) {
        bgmSoundPlayModel.play(hasBGM)
    }

    override fun restart() {
        bgmSoundPlayModel.restart()
    }

    override fun stop() {
        bgmSoundPlayModel.stop()
    }

    override fun setIsOn(isOn: Boolean) {
        bgmSoundPlayModel.setIsOn(isOn)
    }

    override fun isOn(): Boolean {
        return bgmSoundPlayModel.isOn()
    }
}

enum class SoundPlayModelState {
    on, off
}

interface SoundPlayModelImplStateUpdater {
    fun updateBGMSoundPlayModel(soundPlayModelState: SoundPlayModelState, bgmSoundPlayModelContext: BGMSoundPlayModelContext)
}


class BGMSoundPlayModelContext(val context: Context, val soundPlayModelImplStateUpdater: SoundPlayModelImplStateUpdater) {
    var hasBGM: HasBGM? = null
}

class BGMSoundPlayModelOn(val bgmSoundPlayModelContext: BGMSoundPlayModelContext): BGMSoundPlayModel {
    var mediaPlayer: MediaPlayer = MediaPlayer()

    init {
        bgmSoundPlayModelContext.hasBGM?.let {
            play(it)
        }
    }

    override fun play(hasBGM: HasBGM) {
        if(hasBGM.bgm.value == bgmSoundPlayModelContext.hasBGM?.bgm?.value) {
            if(mediaPlayer.isPlaying) {
                mediaPlayer.seekTo(0)
                return
            }
        }

        bgmSoundPlayModelContext.hasBGM = hasBGM
        stop()
        restart()
    }

    override fun restart() {
//        if(mediaPlayer.isPlaying) {
//            return
//        }

        val mediaPlayer = MediaPlayer()
        this.mediaPlayer = mediaPlayer

        bgmSoundPlayModelContext.hasBGM?.let {
            val f = bgmSoundPlayModelContext.context.getAssets().openFd(it.bgm.value)
            mediaPlayer.setDataSource(f.getFileDescriptor(), f.startOffset, f.length)
            mediaPlayer.prepare()
            mediaPlayer.isLooping = true
            mediaPlayer.start()
        }
    }

    override fun stop() {
        mediaPlayer?.let {
            it.stop()
            it.reset()
            it.release()
        }
    }

    override fun setIsOn(isOn: Boolean) {
        if(isOn) {
            return
        }

        stop()
        bgmSoundPlayModelContext.soundPlayModelImplStateUpdater.updateBGMSoundPlayModel(SoundPlayModelState.off, bgmSoundPlayModelContext)
    }

    override fun isOn(): Boolean {
        return true
    }
}

class BGMSoundPlayModelOff(val bgmSoundPlayModelContext: BGMSoundPlayModelContext): BGMSoundPlayModel {
    override fun play(hasBGM: HasBGM) {
        bgmSoundPlayModelContext.hasBGM = hasBGM
    }

    override fun restart() {}
    override fun stop() {}

    override fun setIsOn(isOn: Boolean) {
        if(isOn) {
            bgmSoundPlayModelContext.soundPlayModelImplStateUpdater.updateBGMSoundPlayModel(SoundPlayModelState.on, bgmSoundPlayModelContext)
        }
    }

    override fun isOn(): Boolean {
        return false
    }
}