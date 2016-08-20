package com.naosim.rpgsample;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.naosim.rpgmodel.android.FieldViewModelFactoryImpl;
import com.naosim.rpgmodel.android.MessageViewModelImpl;
import com.naosim.rpgmodel.android.sirokuro.DataSaveRepositoryAndroidImpl;
import com.naosim.rpgmodel.android.GamePadView;
import com.naosim.rpgmodel.lib.GameMain;
import com.naosim.rpgmodel.lib.script.MessageScriptController;
import com.naosim.rpgmodel.lib.value.Item;
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModelFactory;
import com.naosim.rpgmodel.lib.viewmodel.MessageViewModel;
import com.naosim.rpgmodel.sirokuro.SirokuroGame;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GameMain gameMain;

    static GameMain createGameMain(
            FieldViewModelFactory fieldViewModelFactory,
            MessageScriptController messageScriptController,
            SharedPreferences sharedPreferences
    ) {
        return new SirokuroGame(
                fieldViewModelFactory,
                messageScriptController,
                new DataSaveRepositoryAndroidImpl(sharedPreferences)
        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        if(actionBar != null)actionBar.hide();

        setContentView(R.layout.activity_main);

        final MessageViewModel messageViewModel = new MessageViewModelImpl((TextView)findViewById(R.id.text), findViewById(R.id.nextIcon));
        final MessageScriptController c = new MessageScriptController(messageViewModel);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.gc();
            }
        }, 5000);

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.onNext();

            }
        });

        WebView webView = (WebView)findViewById(R.id.webView);
        webView.clearCache(true);

        // GAME MAIN　生成
        this.gameMain = createGameMain(
                new FieldViewModelFactoryImpl(webView),
                c,
                getSharedPreferences("hoge", Context.MODE_PRIVATE)
        );

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/www/index.html?date=" + new Date().getTime());

        GamePadView gamepadView = (GamePadView)findViewById(R.id.gamePadView);
        gamepadView.getAButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMain.onPressAButton();
            }
        });
        gamepadView.getBButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<? extends Item> itemList =  gameMain.getItemList();
                if(itemList.size() == 0) {
                    Toast.makeText(MainActivity.this, "どうぐがありません", Toast.LENGTH_SHORT).show();
                    return;
                }

                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_item, null);
                final AlertDialog alertDialog = new AlertDialog
                        .Builder(v.getContext())
                        .setView(view)
                        .create();

                ListView listView = (ListView)view.findViewById(R.id.itemListView);
                listView.setAdapter(new ItemListAdapter(v.getContext(), itemList));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ItemListAdapter<Item> adapter = (ItemListAdapter<Item>)parent.getAdapter();
                        Item selectedItem = adapter.getItem(position);
                        gameMain.onItemUsed(selectedItem);
                        alertDialog.dismiss();
                    }
                });

                view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });
        gamepadView.getArrowPadView().setFieldViewModel(gameMain.getFieldViewModel());
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.gameMain.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.gameMain.onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.gameMain.onDestroy();
    }
}
