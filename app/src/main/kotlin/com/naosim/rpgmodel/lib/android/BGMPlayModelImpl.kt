package com.naosim.rpgmodel.lib.android

import android.content.Context
import android.media.MediaPlayer
import com.naosim.rpgmodel.lib.model.viewmodel.BGMPlayModel
import com.naosim.rpgmodel.lib.model.viewmodel.HasBGM

class BGMPlayModelImpl(
        val context: Context,
        val bgmPlayModelOnFactory: (BGMPlayModelContext) -> BGMPlayModelOn,
        val bgmPlayModelOffFactory: (BGMPlayModelContext) -> BGMPlayModelOff
): BGMPlayModel {
    val bgmPlayModelContext = BGMPlayModelContext(context)
    var bgmPlayModel: BGMPlayModel = BGMPlayModelOff(bgmPlayModelContext)
//    private var _isOn: Boolean = false
    var isOn: Boolean = false
    get
    set(value) {
        if(field == value) {
            return
        }

        bgmPlayModel.stop()
        bgmPlayModel = if(value) {
            bgmPlayModelOnFactory.invoke(bgmPlayModelContext)
        } else {
            bgmPlayModelOffFactory.invoke(bgmPlayModelContext)
        }
        field = value
    }
    constructor(context: Context) :this(
            context,
            { BGMPlayModelOn(it) },
            { BGMPlayModelOff(it) }
    )

    override fun play(hasBGM: HasBGM) {
        bgmPlayModel.play(hasBGM)
    }

    override fun restart() {
        bgmPlayModel.restart()
    }

    override fun stop() {
        bgmPlayModel.stop()
    }
}

enum class SoundPlayModelState {
    on, off
}

class BGMPlayModelContext(val context: Context) {
    var hasBGM: HasBGM? = null
}

class BGMPlayModelOn(val bgmPlayModelContext: BGMPlayModelContext): BGMPlayModel {
    var mediaPlayer: MediaPlayer = MediaPlayer()

    init {
        bgmPlayModelContext.hasBGM?.let {
            restart()
        }
    }

    override fun play(hasBGM: HasBGM) {
        if(hasBGM.bgm.value == bgmPlayModelContext.hasBGM?.bgm?.value) {
            if(mediaPlayer.isPlaying) {
                mediaPlayer.seekTo(0)
                return
            }
        }

        bgmPlayModelContext.hasBGM = hasBGM
        stop()
        restart()
    }

    override fun restart() {
//        if(mediaPlayer.isPlaying) {
//            return
//        }

        val mediaPlayer = MediaPlayer()
        this.mediaPlayer = mediaPlayer

        bgmPlayModelContext.hasBGM?.let {
            val filename = it.bgm.value
            val f = bgmPlayModelContext.context.getAssets().openFd(filename)
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
}

class BGMPlayModelOff(val bgmPlayModelContext: BGMPlayModelContext): BGMPlayModel {
    override fun play(hasBGM: HasBGM) {
        bgmPlayModelContext.hasBGM = hasBGM
    }

    override fun restart() {}
    override fun stop() {}
}