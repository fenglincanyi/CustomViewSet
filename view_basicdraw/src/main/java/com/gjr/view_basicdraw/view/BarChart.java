package com.gjr.view_basicdraw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by geng
 * on 2017/9/18.
 *
 * 条形图
 */
public class BarChart extends View {


    private Paint paint, paint1, paint2;
    private Path path, path1;

    public BarChart(Context context) {
        this(context, null);
    }

    public BarChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);

        paint1 = new Paint(paint);
        paint1.setColor(Color.GREEN);
        paint1.setStyle(Paint.Style.FILL);

        // 画坐标轴
        path = new Path();
        path.moveTo(50,50);
        path.lineTo(50, 800);
        path.lineTo(700, 800);// 注意：rLineTo 是相对于 上一个终点，再画 (700,800) 的位移的点


        buildRects();

        paint2 = new Paint();
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAntiAlias(true);
        paint2.setTextSize(22f);
        paint2.setColor(Color.WHITE);
    }

    private void buildRects() {

        // 尝试用 path 实现，为了练习 path 而已
        path1 = new Path();
        path1.moveTo(100, 798);

        path1.lineTo(100, 300);
        path1.lineTo(160, 300);
        path1.lineTo(160, 798);

        path1.moveTo(190, 798);

        path1.lineTo(190, 200);
        path1.lineTo(250, 200);
        path1.lineTo(250, 798);

        path1.moveTo(280, 798);

        path1.lineTo(280, 500);
        path1.lineTo(340, 500);
        path1.lineTo(340, 798);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.parseColor("#40626D"));

        paint.setStrokeCap(Paint.Cap.ROUND);// 线头的形状
        paint.setStrokeJoin(Paint.Join.ROUND);// 拐角的形状


        canvas.drawPath(path, paint);


        canvas.drawPath(path1, paint1);


        canvas.drawText("招聘",100,830, paint2);
        canvas.drawText("房产",190,830, paint2);
        canvas.drawText("二手车",280,830, paint2);


    }
}
