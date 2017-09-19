package com.gjr.view_basicdraw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.gjr.view_basicdraw.R;

/**
 * Created by geng
 * on 2017/9/6.
 */
public class BasicGraphView1 extends View {

    private Paint paint;
    private Bitmap bitmap;
    private PorterDuffXfermode xfermode;

    public BasicGraphView1(Context context) {
        this(context, null);
    }

    public BasicGraphView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicGraphView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);// 抗锯齿 alias
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5f);

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.saveLayer(100, 100, 300, 300, null, Canvas.ALL_SAVE_FLAG);// 设置一个 离屏缓冲

        paint.setColor(Color.RED);
        canvas.drawCircle(200, 200, 100, paint);
        paint.setXfermode(xfermode);

        paint.setColor(Color.GREEN);
        canvas.drawRoundRect(new RectF(100, 100, 200, 200), 20, 20, paint);


        canvas.restore();
    }
}

// PorterDuff 文档：
// https://developer.android.com/reference/android/graphics/PorterDuff.Mode.html