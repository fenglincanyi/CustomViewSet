package com.gjr.view_basicdraw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.gjr.view_basicdraw.R;

/**
 * Created by geng
 * on 2017/9/6.
 */
public class BasicGraphView extends View {

    private Paint paint;
    private Bitmap bitmap;

    public BasicGraphView(Context context) {
        this(context, null);
    }

    public BasicGraphView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
//        initFilter();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);// 抗锯齿 alias
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5f);
    }

    private void initFilter() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);

        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0.5f, 0, 0, 0, 0,
                0, 0.5f, 0, 0, 0,
                0, 0, 0.5f, 0, 0,
                0, 0, 0, 1f, 0
        });

        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.parseColor("#22ff00"), PorterDuff.Mode.SRC_OUT);
//        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
//        paint.setColorFilter(new LightingColorFilter(0xff00ff, 0));
        paint.setColorFilter(filter);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawBitmap(bitmap, 0, 0, paint);
//        canvas.drawCircle(300, 300, 200, paint);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.parseColor("#000000"));
//        paint.setStrokeWidth(30f);
//        canvas.drawCircle(300, 300, 200, paint);


//        canvas.drawRect(100, 100, 600, 400, paint);
//        canvas.drawRoundRect(100, 100, 600, 400,5, 5, paint);
//        RectF rectF = new RectF(100, 100, 600, 400);
//        canvas.drawRoundRect(rectF, 20f, 20f,paint);


        // 扇形 或 弧
//        RectF rectF = new RectF(100, 100, 600, 400);
//        canvas.drawArc(rectF, 0f, -120f, true, paint);
//        canvas.drawOval(rectF, paint);

    }
}