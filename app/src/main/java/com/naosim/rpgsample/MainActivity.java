package com.naosim.rpgsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.naosim.rpgmodel.Hello;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Hello("hoge");
    }
}
