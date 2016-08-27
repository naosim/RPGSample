package com.naosim.rpgmodel.lib.android

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import com.naosim.rpgmodel.lib.model.viewmodel.HasSE
import com.naosim.rpgmodel.lib.model.viewmodel.SEPlayModel
import java.util.*

class SEPlayModelImpl (
        val sePlayModelCore: SEPlayModelCore,
        val sePlayModelOnFactory: (SEPlayModelContext) -> SEPlayModelOn,
        val sePlayModelOffFactory: (SEPlayModelContext) -> SEPlayModelOff
): SEPlayModel, SEPlayModelImplStateUpdater {
    var sePlayModel: SEPlayModel = SEPlayModelOff(SEPlayModelContext(sePlayModelCore, this))
    constructor(sePlayModelCore: SEPlayModelCore) :this(
            sePlayModelCore,
            { SEPlayModelOn(it) },
            { SEPlayModelOff(it) }
    )

    override fun updateSEPlayModel(soundPlayModelState: SoundPlayModelState, sePlayModelContext: SEPlayModelContext) {
        this.sePlayModel = when(soundPlayModelState) {
            SoundPlayModelState.on -> sePlayModelOnFactory.invoke(sePlayModelContext)
            SoundPlayModelState.off -> sePlayModelOffFactory.invoke(sePlayModelContext)
        }
    }

    override fun play(hasSE: HasSE) {
        sePlayModel.play(hasSE)
    }

    override fun setIsOn(isOn: Boolean) {
        sePlayModel.setIsOn(isOn)
    }

    override fun isOn(): Boolean {
        return sePlayModel.isOn()
    }
}

fun createSoundPool(): SoundPool {
    val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)// USAGE_MEDIA
            // USAGE_GAME
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)// CONTENT_TYPE_MUSIC
            // CONTENT_TYPE_SPEECH, etc.
            .build()
    return SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(2).build()
}

interface SEPlayModelImplStateUpdater {
    fun updateSEPlayModel(soundPlayModelState: SoundPlayModelState, sePlayModelContext: SEPlayModelContext)
}


class SEPlayModelContext(val sePlayModelCore: SEPlayModelCore, val sePlayModelImplStateUpdater: SEPlayModelImplStateUpdater) {
}


class SEPlayModelCore(val context: Context, hasSEList: List<HasSE>) {
    private val soundPool: SoundPool
    private val soundIdMap: Map<String, Int>

    init {
        val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)// USAGE_MEDIA
                // USAGE_GAME
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)// CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .build()
        this.soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(2).build()
        this.soundIdMap = HashMap()
        hasSEList.map { it.se.value }.forEach {
            val afd = context.assets.openFd(it)
            val soundId = soundPool.load(afd.fileDescriptor, afd.startOffset, afd.length, 0)
            this.soundIdMap.put(it, soundId)
        }
    }


    fun play(hasSE: HasSE) {
        soundIdMap.get(hasSE.se.value)?.let {
            soundPool.play(it, 1.0f, 1.0f, 0, 0, 1f)
        }
    }

    fun release() {
        soundPool.release()
    }
}

class SEPlayModelOn(val sePlayModelContext: SEPlayModelContext): SEPlayModel {
    var mediaPlayer: MediaPlayer = MediaPlayer()
    override fun play(hasSE: HasSE) {
        sePlayModelContext.sePlayModelCore.play(hasSE)
    }

    override fun setIsOn(isOn: Boolean) {
        if(isOn) {
            return
        }
        sePlayModelContext.sePlayModelImplStateUpdater.updateSEPlayModel(SoundPlayModelState.off, sePlayModelContext)
    }

    override fun isOn(): Boolean {
        return true
    }
}

class SEPlayModelOff(val sePlayModelContext: SEPlayModelContext): SEPlayModel {
    override fun play(hasSE: HasSE) { }

    override fun setIsOn(isOn: Boolean) {
        if(isOn) {
            sePlayModelContext.sePlayModelImplStateUpdater.updateSEPlayModel(SoundPlayModelState.on, sePlayModelContext)
        }
    }

    override fun isOn(): Boolean {
        return false
    }
}