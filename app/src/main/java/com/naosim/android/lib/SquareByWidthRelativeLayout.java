package com.naosim.android.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class SquareByWidthRelativeLayout extends RelativeLayout {
    public SquareByWidthRelativeLayout(Context context) {
        super(context);
    }

    public SquareByWidthRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareByWidthRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareByWidthRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
        if(getWidth() != 0) {
            setBottom(getTop() + getWidth());
        }
        Log.e(this.getClass().getSimpleName(), String.format("%d, %d, %d", getTop(), getBottom(), getWidth()));
    }


}
