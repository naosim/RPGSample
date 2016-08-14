package com.naosim.rpgsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.naosim.rpgmodel.android.FieldViewModelFactoryImpl;
import com.naosim.rpgmodel.android.MessageViewModelImpl;
import com.naosim.rpgmodel.lib.script.MessageScriptController;
import com.naosim.rpgmodel.lib.value.field.ArrowButtonType;
import com.naosim.rpgmodel.lib.viewmodel.FieldViewModel;
import com.naosim.rpgmodel.lib.viewmodel.MessageViewModel;
import com.naosim.rpgmodel.sirokuro.SirokuroGame;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    SirokuroGame sirokuroGame;


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


        WebView webView = (WebView)findViewById(R.id.webView);

        this.sirokuroGame = new SirokuroGame(
                new FieldViewModelFactoryImpl(webView),
                c
        );


        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/www/index.html?date=" + new Date().getTime());

        findViewById(R.id.upButton).setOnTouchListener(new ButtonEvent(this.sirokuroGame.getFieldViewModel(), ArrowButtonType.up));
        findViewById(R.id.downButton).setOnTouchListener(new ButtonEvent(this.sirokuroGame.getFieldViewModel(), ArrowButtonType.down));
        findViewById(R.id.rightButton).setOnTouchListener(new ButtonEvent(this.sirokuroGame.getFieldViewModel(), ArrowButtonType.right));
        findViewById(R.id.leftButton).setOnTouchListener(new ButtonEvent(this.sirokuroGame.getFieldViewModel(), ArrowButtonType.left));

        findViewById(R.id.aButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sirokuroGame.onPressAButton();
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
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                fieldViewModel.onButtonDown(arrowButtonType);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                fieldViewModel.onButtonUp(arrowButtonType);
            }
            return true;
        }
    }
}
