package com.naosim.rpgsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.naosim.rpgmodel.android.sirokuro.DataSaveRepositoryAndroidImpl;
import com.naosim.rpgmodel.lib.android.BGMPlayModelImpl;
import com.naosim.rpgmodel.lib.android.FieldViewModelFactoryImpl;
import com.naosim.rpgmodel.lib.android.GamePadView;
import com.naosim.rpgmodel.lib.android.ItemSelectDialogFactory;
import com.naosim.rpgmodel.lib.android.MessageViewModelImpl;
import com.naosim.rpgmodel.lib.android.SEPlayModelCore;
import com.naosim.rpgmodel.lib.android.SEPlayModelImpl;
import com.naosim.rpgmodel.lib.model.GameMain;
import com.naosim.rpgmodel.lib.model.script.MessageScriptController;
import com.naosim.rpgmodel.lib.model.value.Item;
import com.naosim.rpgmodel.lib.model.viewmodel.FieldViewModelFactory;
import com.naosim.rpgmodel.lib.model.viewmodel.MessageViewModel;
import com.naosim.rpgmodel.lib.model.viewmodel.sound.bgm.BGMPlayModel;
import com.naosim.rpgmodel.lib.model.viewmodel.sound.se.HasSE;
import com.naosim.rpgmodel.lib.model.viewmodel.sound.se.SEPlayModel;
import com.naosim.rpgmodel.sirokuro.SirokuroGame;
import com.naosim.rpgmodel.sirokuro.SirokuroSE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    GameMain gameMain;
    WebView webView;
    final ItemSelectDialogFactory itemSelectDialogFactory = new ItemSelectDialogFactory();
    BGMPlayModelImpl bgmPlayModelImpl;
    SEPlayModelImpl sePlayModelImpl;
    SEPlayModelCore sePlayModelCore;

    static GameMain createGameMain(
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

    static final String ASSET_HTML_URL = "file:///android_asset/www/index.html";
    WebView createWebView() {
        WebView webView = (WebView)findViewById(R.id.webView);
        webView.clearCache(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(ASSET_HTML_URL + "?date=" + new Date().getTime());
        return webView;
    }

    SharedPreferences getSharedPreferences() {
        return getSharedPreferences("hoge", Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActionBar() != null)getActionBar().hide();
        if(getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.gc();
            }
        }, 5000);

        final MessageViewModel messageViewModel = new MessageViewModelImpl((TextView)findViewById(R.id.text), findViewById(R.id.nextIcon));
        final MessageScriptController c = new MessageScriptController(messageViewModel);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.onNext();

            }
        });

        this.webView = createWebView();
        this.bgmPlayModelImpl =  new BGMPlayModelImpl(this);

        List<HasSE> list = new ArrayList<>();
        list.add(SirokuroSE.se1);
        sePlayModelCore = new SEPlayModelCore(this, list);
        sePlayModelImpl = new SEPlayModelImpl(sePlayModelCore);

        // GAME MAIN　生成
        this.gameMain = createGameMain(
                new FieldViewModelFactoryImpl(this.webView),
                c,
                getSharedPreferences(),
                this.bgmPlayModelImpl,
                sePlayModelImpl
        );

        // ゲームパッド
        GamePadView gamepadView = (GamePadView)findViewById(R.id.gamePadView);
        gamepadView.getArrowPadView().setFieldViewModel(gameMain.getFieldViewModel());
        gamepadView.getAButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMain.onPressAButton();
            }
        });
        gamepadView.getBButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelectDialogFactory.showItemListDialog(v.getContext(), gameMain.getItemList(), new Function1<Item, Unit>() {
                    @Override
                    public Unit invoke(Item item) {
                        gameMain.onItemUsed(item);
                        return null;
                    }
                });
            }
        });

        gamepadView.getSettingButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newIsOn = !bgmPlayModelImpl.isOn();
                bgmPlayModelImpl.setOn(newIsOn);
                getSharedPreferences().edit().putBoolean("isBGMOn", newIsOn).commit();
                getSharedPreferences().edit().putBoolean("isSEOn", newIsOn).commit();
//                sePlayModelCore.play(SETest.se1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameMain.onStart();
        bgmPlayModelImpl.setOn(getSharedPreferences().getBoolean("isBGMOn", true));
        sePlayModelImpl.setOn(getSharedPreferences().getBoolean("isSEOn", true));
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.gameMain.onDestroy();
        webView.pauseTimers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameMain.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.gameMain.onDestroy();
        sePlayModelCore.release();
    }
}
