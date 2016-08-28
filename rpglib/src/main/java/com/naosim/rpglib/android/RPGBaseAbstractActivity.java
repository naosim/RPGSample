package com.naosim.rpglib.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.naosim.rpglib.R;
import com.naosim.rpglib.android.fieldviewmodel.FieldViewModelFactoryImpl;
import com.naosim.rpglib.model.GameMain;
import com.naosim.rpglib.model.script.MessageScriptController;
import com.naosim.rpglib.model.value.Item;
import com.naosim.rpglib.model.viewmodel.fieldviewmodel.FieldViewModelFactory;
import com.naosim.rpglib.model.viewmodel.MessageViewModel;
import com.naosim.rpglib.model.viewmodel.sound.bgm.BGMPlayModel;
import com.naosim.rpglib.model.viewmodel.sound.se.HasSE;
import com.naosim.rpglib.model.viewmodel.sound.se.SEPlayModel;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * おすすめの設定です。
 * コピペするも良し、継承するも良し
 */
abstract public class RPGBaseAbstractActivity extends AppCompatActivity {
    GameMain gameMain;
    WebView webView;
    final ItemSelectDialogFactory itemSelectDialogFactory = new ItemSelectDialogFactory();
    BGMPlayModelImpl bgmPlayModelImpl;
    SEPlayModelImpl sePlayModelImpl;
    SEPlayModelCore sePlayModelCore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActionBar() != null)getActionBar().hide();
        if(getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(getContentView());
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

        sePlayModelCore = new SEPlayModelCore(this, createSEList());
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

    int getContentView() {
        return R.layout.activity_rpgbase;
    }

    abstract public GameMain createGameMain(
            FieldViewModelFactory fieldViewModelFactory,
            MessageScriptController messageScriptController,
            SharedPreferences sharedPreferences,
            BGMPlayModel bgmPlayModel,
            SEPlayModel sePlayModel
    );
    abstract public WebView createWebView();
    abstract public List<HasSE> createSEList();
    abstract public SharedPreferences getSharedPreferences();
}
