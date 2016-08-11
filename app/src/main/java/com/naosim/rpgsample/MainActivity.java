package com.naosim.rpgsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.naosim.rpgmodel.android.ItemSelectorViewModelImpl;
import com.naosim.rpgmodel.android.MessageViewModelImpl;
import com.naosim.rpgmodel.android.WebFieldViewModelImpl;
import com.naosim.rpgmodel.lib.script.MessageScriptController;
import com.naosim.rpgmodel.lib.value.ItemSet;
import com.naosim.rpgmodel.lib.value.field.ArrowButtonType;
import com.naosim.rpgmodel.lib.value.field.FieldData;
import com.naosim.rpgmodel.lib.value.field.FieldLayer;
import com.naosim.rpgmodel.lib.value.field.FieldLayerName;
import com.naosim.rpgmodel.lib.value.field.FieldName;
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModel;
import com.naosim.rpgmodel.lib.viewmodel.ItemSelectorViewModel;
import com.naosim.rpgmodel.lib.viewmodel.MessageViewModel;
import com.naosim.rpgmodel.sirokuro.charactor.GameItem;
import com.naosim.rpgmodel.sirokuro.charactor.KuroYagi;
import com.naosim.rpgmodel.sirokuro.charactor.Player;
import com.naosim.rpgmodel.sirokuro.charactor.SiroYagi;
import com.naosim.rpgmodel.sirokuro.global.GlobalContainer;
import com.naosim.rpgmodel.sirokuro.global.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    WebFieldViewModelImpl webFieldViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MessageViewModel messageViewModel = new MessageViewModelImpl((TextView)findViewById(R.id.text), findViewById(R.id.nextIcon));
        final MessageScriptController c = new MessageScriptController(messageViewModel);



        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.onNext();

            }
        });

        ItemSet<GameItem> itemSet = new ItemSet<>();
        itemSet.add(GameItem.やくそう);
        itemSet.add(GameItem.やくそう);
        itemSet.add(GameItem.やくそう);

        GlobalContainer gc = new GlobalContainer(c, new Status(), itemSet);
        final KuroYagi kuro = new KuroYagi(gc);
//        findViewById(R.id.charaKuro).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                kuro.check();
//            }
//        });

        final SiroYagi siro = new SiroYagi(gc);
//        findViewById(R.id.charaSiro).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                siro.check();
//            }
//        });

        final Player player = new Player(gc);

        final ItemSelectorViewModel<GameItem> i = new ItemSelectorViewModelImpl(
                (Button)findViewById(R.id.yakusouButton),
                gc.getItemSet(),
                new Function1<GameItem, Unit>() {
                    @Override
                    public Unit invoke(GameItem gameItem) {
                        player.useItem(gameItem);
                        return null;
                    }
                }
        );

        WebView webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/www/index.html?date=" + new Date().getTime());
        webFieldViewModel = new WebFieldViewModelImpl(webView);

//        findViewById(R.id.aButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                webFieldViewModel.getPosition(new Function1<Position, Unit>() {
//                    @Override
//                    public Unit invoke(Position position) {
//                        Log.e("WebFieldViewModelImpl", position.toString());
//                        return null;
//                    }
//                });
//            }
//        });

        findViewById(R.id.upButton).setOnTouchListener(new ButtonEvent(webFieldViewModel, ArrowButtonType.up));
        findViewById(R.id.downButton).setOnTouchListener(new ButtonEvent(webFieldViewModel, ArrowButtonType.down));
        findViewById(R.id.rightButton).setOnTouchListener(new ButtonEvent(webFieldViewModel, ArrowButtonType.right));
        findViewById(R.id.leftButton).setOnTouchListener(new ButtonEvent(webFieldViewModel, ArrowButtonType.left));

        findViewById(R.id.aButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<List<Integer>> l = new ArrayList<List<Integer>>();
                ArrayList<Integer> l1 = new ArrayList<Integer>();
                l1.add(1);l1.add(2);
                ArrayList<Integer> l2 = new ArrayList<Integer>();
                l2.add(3);l2.add(4);
                l.add(l1); l.add(l2);
                webFieldViewModel.updateFieldLayer(
                        new FieldLayer(
                                new FieldName("aaa"),
                                new FieldLayerName("back"),
                                new FieldData(l)
                        )
                );
//                webFieldViewModel.gotoPosition(new Position(
//                        new FieldName("aaa"),
//                        new X(10),
//                        new Y(20)
//                ));
            }
        });

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
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                fieldViewModel.onButtonDown(arrowButtonType);
            } else if(event.getAction() == MotionEvent.ACTION_UP) {
                fieldViewModel.onButtonUp(arrowButtonType);
            }
            return true;
        }
    }
}
