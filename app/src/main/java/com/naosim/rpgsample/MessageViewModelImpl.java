package com.naosim.rpgsample;

import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.naosim.rpgmodel.lib.value.Message;
import com.naosim.rpgmodel.lib.viewmodel.MessageViewModel;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * Created by nao_pillows on 2016/08/06.
 */
public class MessageViewModelImpl implements MessageViewModel {
    private final TextView textView;
    private final Handler handler = new Handler();
    private final View nextIcon;

    public MessageViewModelImpl(TextView textView, View nextIcon) {
        this.textView = textView;
        this.nextIcon = nextIcon;
        dismiss();
    }

    public void setMessageText(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }

    @Override
    public void clear() {
        textView.setText("");
    }

    @Override
    public void appendText(@NotNull Message message, final @NotNull Function0<Unit> onEnd) {
        String text = message.getValue().trim() + "\n";
        final int firstTextCount = textView.getText().toString().length();
        final char data[] = text.toCharArray();

        nextIcon.setVisibility(View.INVISIBLE);
        nextIcon.clearAnimation();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    String currentText = textView.getText().toString();
                    int i = currentText.length();
                    if(i - firstTextCount >= data.length) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                nextIcon.setVisibility(View.VISIBLE);
                                AlphaAnimation aanim1 = new AlphaAnimation(1, 0.2f);
                                aanim1.setDuration( 400 );
                                aanim1.setRepeatCount( Animation.INFINITE );
                                aanim1.setRepeatMode( Animation.REVERSE );
                                (nextIcon).startAnimation(aanim1);

                                onEnd.invoke();
                            }
                        });
                        return;
                    }
                    setMessageText(currentText + data[i++ - firstTextCount]);
                    try {
                        Thread.sleep(50L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }).start();
    }

    @Override
    public void show() {
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismiss() {
        textView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isShowing() {
        return textView.getVisibility() == View.VISIBLE;
    }
}
