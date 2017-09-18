package com.gjr.view_basicdraw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by geng
 * on 2017/9/18.
 * <p>
 * 饼图
 */
public class PieChart extends View {

    private Paint paint, paint1, paint2;
    private RectF r1;
    private Path path;

    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);

        r1 = new RectF(150, 150, 600, 600);

        path = new Path();
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.STROKE);// 必须要设置成 stroke ，否则下面的 canvas 画不出来 path
        paint1.setStrokeWidth(3f);
        paint1.setColor(Color.WHITE);


        paint2 = new Paint(paint1);
        paint2.setStrokeWidth(0f);
        paint2.setTextSize(18f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.parseColor("#40626D"));

        paint.setColor(Color.RED);
        canvas.drawArc(r1, 0, 30, true, paint);

        paint.setColor(Color.GREEN);
        canvas.drawArc(r1, 30, 10, true, paint);

        paint.setColor(Color.GRAY);
        canvas.drawArc(r1, 40, 40, true, paint);

        paint.setColor(Color.BLUE);
        r1.offset(-8, 0);
        canvas.drawArc(r1, 80, 90, true, paint);

        paint.setColor(Color.BLACK);
        r1.offset(-5, -16);
        canvas.drawArc(r1, 170, 120, true, paint);


        paint.setColor(Color.MAGENTA);
        r1.offset(13, 16);
        canvas.drawArc(r1, 290, 70, true, paint);

        // 画线
        path.moveTo(200, 200);
        path.lineTo(180, 180);
        path.lineTo(120, 180);
        canvas.drawPath(path, paint1);
        canvas.drawText("LOLLIPOP", 30, 186, paint2);

        // 画线
        path.moveTo(210, 540);
        path.lineTo(170, 570);
        path.lineTo(120, 570);
        canvas.drawPath(path, paint1);
        canvas.drawText("KITKAT", 50, 574, paint2);

    }
}
