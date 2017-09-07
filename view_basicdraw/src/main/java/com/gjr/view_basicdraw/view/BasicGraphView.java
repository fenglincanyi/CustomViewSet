package com.gjr.view_basicdraw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by geng
 * on 2017/9/6.
 */
public class BasicGraphView extends View {

    public BasicGraphView(Context context) {
        super(context);
    }

    public BasicGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasicGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
