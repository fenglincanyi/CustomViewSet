package com.gjr.view_basicdraw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.gjr.view_basicdraw.R;

/**
 * Created by geng
 * on 2017/9/6.
 */
public class BasicGraphView1 extends View {

    private Paint paint;
    private Path path;

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
//        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);


        path = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.moveTo(100, 100);
        path.lineTo(400, 100);// 以(0，0) 为起点，传入的参数为重点画直线
//        path.rLineTo(); // 参考上一个点，相对的画直线，如果没有上一个点，则默认以(0,0)为上一个点
        path.lineTo(250, 200);
        path.close();// 闭合起点和终点

        canvas.drawPath(path, paint);


    }
}
















