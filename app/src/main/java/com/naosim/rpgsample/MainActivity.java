package com.naosim.rpgsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.naosim.rpgmodel.android.ItemSelectorViewModelImpl;
import com.naosim.rpgmodel.lib.script.MessageScriptController;
import com.naosim.rpgmodel.lib.value.ItemSet;
import com.naosim.rpgmodel.lib.viewmodel.ItemSelectorViewModel;
import com.naosim.rpgmodel.lib.viewmodel.MessageViewModel;
import com.naosim.rpgmodel.sirokuro.charactor.GameItem;
import com.naosim.rpgmodel.sirokuro.charactor.KuroYagi;
import com.naosim.rpgmodel.sirokuro.charactor.Player;
import com.naosim.rpgmodel.sirokuro.charactor.SiroYagi;
import com.naosim.rpgmodel.sirokuro.global.GlobalContainer;
import com.naosim.rpgmodel.sirokuro.global.Status;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
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
        findViewById(R.id.charaKuro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kuro.check();
            }
        });

        final SiroYagi siro = new SiroYagi(gc);
        findViewById(R.id.charaSiro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siro.check();
            }
        });

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



    }
}
