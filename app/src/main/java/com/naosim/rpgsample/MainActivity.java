package com.naosim.rpgsample;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.naosim.rpgmodel.android.FieldViewModelFactoryImpl;
import com.naosim.rpgmodel.android.MessageViewModelImpl;
import com.naosim.rpgmodel.android.sirokuro.DataSaveRepositoryAndroidImpl;
import com.naosim.rpgmodel.android.sirokuro.GamePadView;
import com.naosim.rpgmodel.lib.script.MessageScriptController;
import com.naosim.rpgmodel.lib.value.field.ArrowButtonType;
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModel;
import com.naosim.rpgmodel.lib.viewmodel.MessageViewModel;
import com.naosim.rpgmodel.sirokuro.SirokuroGame;
import com.naosim.rpgmodel.sirokuro.charactor.GameItem;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SirokuroGame sirokuroGame;


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
        DataSaveRepositoryAndroidImpl dataSaveRepository = new DataSaveRepositoryAndroidImpl(getSharedPreferences("hoge", Context.MODE_PRIVATE));
        this.sirokuroGame = new SirokuroGame(
                new FieldViewModelFactoryImpl(webView),
                c,
                dataSaveRepository
        );
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/www/index.html?date=" + new Date().getTime());

        GamePadView gamepadView = (GamePadView)findViewById(R.id.gamePadView);
        gamepadView.getAButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sirokuroGame.onPressAButton();
            }
        });
        gamepadView.getBButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<GameItem> itemList =  sirokuroGame.getGlobalContainer().getItemSet().getList();
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
                listView.setAdapter(new GameItemListAdapter(v.getContext(), itemList));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        GameItemListAdapter adapter = (GameItemListAdapter)parent.getAdapter();
                        GameItem selectedGameItem = adapter.getItem(position);
                        sirokuroGame.onItemUsed(selectedGameItem);
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
        gamepadView.getArrowPadView().setFieldViewModel(sirokuroGame.getFieldViewModel());


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MainActivity", "onPause");
        this.sirokuroGame.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainActivity", "onStop");
        this.sirokuroGame.onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MainActivity", "onDestroy");
        this.sirokuroGame.onDestroy();
    }

    static class ButtonEvent implements View.OnTouchListener {
        public final FieldViewModel fieldViewModel;
        private final ArrowButtonType arrowButtonType;

        ButtonEvent(FieldViewModel fieldViewModel, ArrowButtonType arrowButtonType) {
            this.fieldViewModel = fieldViewModel;
            this.arrowButtonType = arrowButtonType;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                fieldViewModel.onButtonDown(arrowButtonType);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                fieldViewModel.onButtonUp(arrowButtonType);
            }
            return true;
        }
    }

    public static class GameItemListAdapter extends ArrayAdapter<GameItem> {

        public GameItemListAdapter(Context context, List<GameItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_item_row, null);
            }

            GameItem gameItem = getItem(position);
            ((TextView)convertView.findViewById(R.id.itemTextView)).setText(gameItem.getItemName().getValue());

            return convertView;
        }
    }
}
