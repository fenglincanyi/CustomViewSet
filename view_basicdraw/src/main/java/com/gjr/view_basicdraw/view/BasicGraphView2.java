package com.gjr.view_basicdraw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.gjr.view_basicdraw.R;

/**
 * Created by geng
 * on 2017/9/6.
 */
public class BasicGraphView2 extends View {

    private Paint paint;
    private Path path;

    public BasicGraphView2(Context context) {
        this(context, null);
    }

    public BasicGraphView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicGraphView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);// 抗锯齿 alias
        paint.setStrokeWidth(5f);


        // 设置外接圆  起点，终点
//        LinearGradient linearGradient = new LinearGradient(300, 300, 500, 500, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);//渐变色
//        LinearGradient linearGradient = new LinearGradient(300, 300, 500, 500, Color.RED, Color.GREEN, Shader.TileMode.MIRROR);//渐变色
//        LinearGradient linearGradient = new LinearGradient(300, 300, 500, 500, Color.RED, Color.GREEN, Shader.TileMode.REPEAT);//渐变色
//        paint.setShader(linearGradient);


        // 图片渐变，圆形，或者别的
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.company_im);
//        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        paint.setShader(bitmapShader);


        // 辐射渐变
//        RadialGradient radialGradient = new RadialGradient(150, 150, 100, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
//        paint.setShader(radialGradient);

        // 扫描渐变，逆时针的顺序渐变颜色
//        SweepGradient sweepGradient = new SweepGradient(150, 150, Color.RED, Color.GREEN);
//        paint.setShader(sweepGradient);


        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.activity_aboutus);
        BitmapShader bitmapShader1 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.company_im);
        BitmapShader bitmapShader2 = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 组合着色
        ComposeShader composeShader = new ComposeShader(bitmapShader2, bitmapShader1, PorterDuff.Mode.OVERLAY);
        paint.setShader(composeShader);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);
        canvas.drawCircle(150, 150, 100, paint);
//        canvas.drawRoundRect(new RectF(200, 200, 400, 400), 20, 20, paint);
    }
}
















