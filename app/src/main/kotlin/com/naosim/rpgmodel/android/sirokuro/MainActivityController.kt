package com.naosim.rpgmodel.android.sirokuro

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.naosim.rpglib.android.activity.RPGBaseActivityController
import com.naosim.rpglib.model.GameMain
import com.naosim.rpglib.model.script.MessageScriptController
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModelFactory
import com.naosim.rpglib.model.viewmodel.sound.bgm.BGMPlayModel
import com.naosim.rpglib.model.viewmodel.sound.se.HasSE
import com.naosim.rpglib.model.viewmodel.sound.se.SEPlayModel
import com.naosim.rpgmodel.sirokuro.SirokuroGame
import com.naosim.rpgmodel.sirokuro.SirokuroSE
import java.util.*

class MainActivityController(activity: Activity) : RPGBaseActivityController(activity) {
    override fun createGameMain(
            fieldViewModelFactory: FieldViewModelFactory,
            messageScriptController: MessageScriptController,
            sharedPreferences: SharedPreferences, bgmPlayModel:
            BGMPlayModel, sePlayModel: SEPlayModel): GameMain {
        return SirokuroGame(
                fieldViewModelFactory,
                messageScriptController,
                DataSaveRepositoryAndroidImpl(sharedPreferences),
                bgmPlayModel,
                sePlayModel)
    }

    override fun getWebViewUrl(): String {
        return "file:///android_asset/www/index.html" + "?date=" + Date().time
    }

    override fun createSEList(): List<HasSE> {
        val list = ArrayList<HasSE>()
        list.add(SirokuroSE.se1)
        return list
    }

    override fun getSharedPreferences(): SharedPreferences {
        return activity.getSharedPreferences("hoge", Context.MODE_PRIVATE)
    }

}

//open public class EmptyModelConverter<A>: ModelConverter3<A, JSONObject> {
//    override fun encode(a: A): JSONObject {
//        return JSONObject()
//    }
//
//    override fun decode(b: JSONObject): A {
//        return null!!
//    }
//
//}