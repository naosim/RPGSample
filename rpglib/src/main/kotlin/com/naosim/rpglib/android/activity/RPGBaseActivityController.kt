package com.naosim.rpglib.android.activity

import android.app.Activity
import android.content.SharedPreferences
import android.support.annotation.IdRes
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.naosim.rpglib.R
import com.naosim.rpglib.android.*
import com.naosim.rpglib.android.fieldviewmodel.FieldViewModelFactoryImpl
import com.naosim.rpglib.model.GameMain
import com.naosim.rpglib.model.script.MessageScriptController
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModelFactory
import com.naosim.rpglib.model.viewmodel.sound.bgm.BGMPlayModel
import com.naosim.rpglib.model.viewmodel.sound.se.HasSE
import com.naosim.rpglib.model.viewmodel.sound.se.SEPlayModel

abstract class RPGBaseActivityController(val activity: Activity): ActivityLifeCycle {
    internal val gameMain: GameMain
    internal val webView: WebView
    internal val itemSelectDialogFactory = ItemSelectDialogFactory()
    internal val bgmPlayModelImpl: BGMPlayModelImpl
    internal val sePlayModelImpl: SEPlayModelImpl
    internal val sePlayModelCore: SEPlayModelCore

    fun findViewById(@IdRes resId: Int): View {
        return activity.findViewById(resId);
    }

    init {
        val messageViewModel = MessageViewModelImpl((findViewById(R.id.text) as TextView?)!!, findViewById(R.id.nextIcon)!!)
        val c = MessageScriptController(messageViewModel)
        findViewById(R.id.text)!!.setOnClickListener({ c.onNext() })

        this.webView = createWebView()
        this.bgmPlayModelImpl = BGMPlayModelImpl(activity)

        sePlayModelCore = SEPlayModelCore(activity, createSEList())
        sePlayModelImpl = SEPlayModelImpl(sePlayModelCore)

        // GAME MAIN　生成
        this.gameMain = createGameMain(
                FieldViewModelFactoryImpl(this.webView),
                c,
                getSharedPreferences(),
                this.bgmPlayModelImpl,
                sePlayModelImpl)

        // ゲームパッド
        val gamepadView = findViewById(R.id.gamePadView) as GamePadView?
        gamepadView!!.arrowPadView.fieldViewModel = gameMain.fieldViewModel
        gamepadView.aButton.setOnClickListener { gameMain.onPressAButton() }
        gamepadView.bButton.setOnClickListener { v ->
            itemSelectDialogFactory.showItemListDialog(v.context, gameMain.getItemList(), { item ->
                gameMain.onItemUsed(item)
                null
            })
        }

        gamepadView.settingButton.setOnClickListener {
            val newIsOn = !bgmPlayModelImpl.isOn
            bgmPlayModelImpl.isOn = newIsOn
            getSharedPreferences().edit().putBoolean("isBGMOn", newIsOn).commit()
            getSharedPreferences().edit().putBoolean("isSEOn", newIsOn).commit()
        }
    }
    override fun onStart() {
        gameMain.onStart()
        bgmPlayModelImpl.isOn = getSharedPreferences().getBoolean("isBGMOn", true)
        sePlayModelImpl.isOn = getSharedPreferences().getBoolean("isSEOn", true)
    }
    override fun onResume() {
        webView.resumeTimers()
    }
    override fun onPause() {
        webView.pauseTimers()
    }
    override fun onStop() {
        gameMain.onStop()
    }
    override fun onDestroy() {
        gameMain.onDestroy()
        sePlayModelCore.release()
    }

    abstract fun createGameMain(
            fieldViewModelFactory: FieldViewModelFactory,
            messageScriptController: MessageScriptController,
            sharedPreferences: SharedPreferences,
            bgmPlayModel: BGMPlayModel,
            sePlayModel: SEPlayModel): GameMain

    abstract fun createWebView(): WebView
    abstract fun createSEList(): List<HasSE>
    abstract fun getSharedPreferences(): SharedPreferences
}