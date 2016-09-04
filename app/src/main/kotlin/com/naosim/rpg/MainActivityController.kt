package com.naosim.rpg

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.naosim.rpg.android.mogura.MoguraDataSaveRepositoryAndroidImpl
import com.naosim.rpg.model.mogura.MoguraGame
import com.naosim.rpg.model.sirokuro.SirokuroSE
import com.naosim.rpglib.android.activity.RPGBaseActivityController
import com.naosim.rpglib.model.GameMain
import com.naosim.rpglib.model.script.MessageScriptController
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModel
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModelFactory
import com.naosim.rpglib.model.viewmodel.sound.bgm.BGMPlayModel
import com.naosim.rpglib.model.viewmodel.sound.se.HasSE
import com.naosim.rpglib.model.viewmodel.sound.se.SEPlayModel
import java.util.*

class MainActivityController(activity: Activity) : RPGBaseActivityController(activity) {
    override fun createGameMain(
            fieldViewModelFactory: FieldViewModelFactory<FieldViewModel>,
            messageScriptController: MessageScriptController,
            sharedPreferences: SharedPreferences, bgmPlayModel:
            BGMPlayModel, sePlayModel: SEPlayModel): GameMain<FieldViewModel> {
        return MoguraGame(
                fieldViewModelFactory,
                messageScriptController,
                MoguraDataSaveRepositoryAndroidImpl(sharedPreferences),
                bgmPlayModel,
                sePlayModel)

//        return SirokuroGame(
//                fieldViewModelFactory,
//                messageScriptController,
//                DataSaveRepositoryAndroidImpl(sharedPreferences),
//                bgmPlayModel,
//                sePlayModel)
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