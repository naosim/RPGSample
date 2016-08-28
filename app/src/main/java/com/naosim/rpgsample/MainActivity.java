package com.naosim.rpgsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.WebView;

import com.naosim.rpglib.android.RPGBaseAbstractActivity;
import com.naosim.rpglib.model.GameMain;
import com.naosim.rpglib.model.script.MessageScriptController;
import com.naosim.rpglib.model.viewmodel.FieldViewModelFactory;
import com.naosim.rpglib.model.viewmodel.sound.bgm.BGMPlayModel;
import com.naosim.rpglib.model.viewmodel.sound.se.HasSE;
import com.naosim.rpglib.model.viewmodel.sound.se.SEPlayModel;
import com.naosim.rpgmodel.android.sirokuro.DataSaveRepositoryAndroidImpl;
import com.naosim.rpgmodel.sirokuro.SirokuroGame;
import com.naosim.rpgmodel.sirokuro.SirokuroSE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends RPGBaseAbstractActivity {
    @Override
    public GameMain createGameMain(
            FieldViewModelFactory fieldViewModelFactory,
            MessageScriptController messageScriptController,
            SharedPreferences sharedPreferences,
            BGMPlayModel bgmPlayModel,
            SEPlayModel sePlayModel
    ) {
        return new SirokuroGame(
                fieldViewModelFactory,
                messageScriptController,
                new DataSaveRepositoryAndroidImpl(sharedPreferences),
                bgmPlayModel,
                sePlayModel
        );
    }

    @Override
    public List<HasSE> createSEList() {
        List<HasSE> list = new ArrayList<>();
        list.add(SirokuroSE.se1);
        return list;
    }

    static final String ASSET_HTML_URL = "file:///android_asset/www/index.html";
    @Override
    public WebView createWebView() {
        WebView webView = (WebView)findViewById(R.id.webView);
        webView.clearCache(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(ASSET_HTML_URL + "?date=" + new Date().getTime());
        return webView;
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences("hoge", Context.MODE_PRIVATE);
    }

}
