package com.naosim.rpglib.android

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.naosim.rpglib.model.viewmodel.sound.se.HasSE
import com.naosim.rpglib.model.viewmodel.sound.se.SEPlayModel
import java.util.*

class SEPlayModelImpl (
        val sePlayModelCore: SEPlayModelCore,
        val sePlayModelOnFactory: (SEPlayModelContext) -> SEPlayModelOn,
        val sePlayModelOffFactory: (SEPlayModelContext) -> SEPlayModelOff
): SEPlayModel {
    val bgmPlayModelContext = SEPlayModelContext(sePlayModelCore)
    var sePlayModel: SEPlayModel = SEPlayModelOff(bgmPlayModelContext)
    var isOn: Boolean = false
        get
        set(value) {
            if(field == value) {
                return
            }

            sePlayModel = if(value) {
                sePlayModelOnFactory.invoke(bgmPlayModelContext)
            } else {
                sePlayModelOffFactory.invoke(bgmPlayModelContext)
            }
            field = value
        }

    constructor(sePlayModelCore: SEPlayModelCore) :this(
            sePlayModelCore,
            { SEPlayModelOn(it) },
            { SEPlayModelOff(it) }
    )

    override fun play(hasSE: HasSE) {
        sePlayModel.play(hasSE)
    }

    fun release() {
        this.sePlayModelCore.release()
    }
}

class SEPlayModelContext(val sePlayModelCore: SEPlayModelCore)

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
    override fun play(hasSE: HasSE) {
        sePlayModelContext.sePlayModelCore.play(hasSE)
    }
}

class SEPlayModelOff(val sePlayModelContext: SEPlayModelContext): SEPlayModel {
    override fun play(hasSE: HasSE) { }
}