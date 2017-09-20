package com.gjr.view_basicdraw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.gjr.view_basicdraw.R;

import java.util.Arrays;

/**
 * Created by geng
 * on 2017/9/6.
 */
public class BasicGraphView3 extends View {

    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private Matrix matrix;
    private Camera camera;

    public BasicGraphView3(Context context) {
        this(context, null);
    }

    public BasicGraphView3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicGraphView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);// 抗锯齿 alias

        path = new Path();
        path.lineTo(200, 200);
        path.lineTo(0, 200);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.activity_aboutus);


        matrix = new Matrix();
        camera = new Camera();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.save();// 保存canvas的绘制属性状态
//        paint.setColor(Color.RED);
//        canvas.clipRect(new RectF(100, 100, 300,300));
//        canvas.drawCircle(300, 300, 300, paint);
//        canvas.restore();
//
//        canvas.save();
//        paint.setColor(Color.GREEN);
//        canvas.clipPath(path);
//        canvas.drawCircle(300, 300, 300, paint);
//        canvas.restore();


        // 几何变换

//        canvas.save();
//        canvas.translate(100,500);
//        canvas.rotate(20);
//        canvas.scale(0.5f,1f);
//        canvas.skew(1f, 0.5f);// 错切
//        canvas.drawBitmap(bitmap, 20, 20, paint);
//        canvas.restore();


        // 使用 matrix 进行几何变换
        // set、reset 方法 :  设置新的 matrix到matrix； 或 重置

        // 数值计算
//        float[] arr = {0, 0, 10, 10, 20, 20};
//        matrix.setScale(0.3f,  0.4f);
//        matrix.mapPoints(arr);
//        System.out.println(Arrays.toString(arr));// I/System.out: [0.0, 0.0, 3.0, 4.0, 6.0, 8.0]

        // 变换
//        matrix.preTranslate(100, 100);
//        Matrix matrix1 = new Matrix();
//        matrix1.preTranslate(200, 200);
//        canvas.setMatrix(matrix);
//        canvas.concat(matrix1);//  或者：   matrix.preConcat(matrix1);
//        canvas.drawBitmap(bitmap, 100, 100, paint);


        // 自定义变换： 控制点变换  matrix.setPolyToPoly()



        // 3D 变换
        canvas.save();

        camera.save(); // 保存 Camera 的状态
        camera.rotateX(40); // 旋转 Camera 的三维空间
        canvas.translate(200, 200); // 旋转之后把投影移动回来
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        canvas.translate(-200, -200); // 旋转之前把绘制内容移动到轴心（原点）
        camera.restore(); // 恢复 Camera 的状态

        // 原理解释：
        // 我们要在 画布（白纸上）画图，画出一个立体的东东；  我们将 画布（白纸）移动，移动到 画布的左上角的那个点  和  将要画的图形的中心点 重合，然后 camera 做投影操作（对做了旋转后），我们将投影记录下来，
        // 把投影绘制到 画布上， 我们再进行还原操作，即 将画布 移动回原来原点的位置，这时候，3D效果的投影就正常的落在正确的位置上了

        // 要注意，此过程 移动 canvas 的目的就是为了 间接的移动  camera

        //  Camera 默认的位置： (0, 0, -8)（英寸） 1英寸 = 72 px   所以默认位置是:  (0, 0, -576)  左手坐标系（垂直向屏幕内 为 正）   http://www.gcssloop.com/customview/matrix-3d-camera
        //  当 看到的 3D 效果 比较大，比较夸张时， 可以设置 location    camera.setLocation(0, 0, 10);

        canvas.drawBitmap(bitmap, 100, 100, paint);
        canvas.restore();

    }
}
// 注意：
// save和restore 操作都是对 matrix/clip 之后的状态控制，所以要对下一个操作生效，必须要控制状态;
//    如果不进行 save和restore，则会按照上次的

// 几何变换 和 裁切 必须在 canvas 绘制之前设置，否则做的操作不生效


// matrix 辅助 canvas 做几何变换，几乎所有的 2D 的变换，都可以使用 matrix 实现
// matrix 是一个 3x3 矩阵，用来描述、计算图形，通过矩阵运算，来达到图形的变换
// 注意：画布操作是对Matrix的封装，Matrix作为更接近底层的东西

// 运算规则：
// 如preScale() 操作 ：M' = M * S(sx, sy, px, py)，
// 总结出规律： preXXX()： 右乘 ；   postXXX()： 左乘     setXXX(): 直接设置矩阵中的数值
// translate、rotate、skew 都是 基于 contact 操作

// 在构造 Matrix 时，个人建议尽量使用一种乘法，前乘或者后乘，这样操作顺序容易确定，出现问题也比较容易排查。
// 当然，由于矩阵乘法不满足交换律，前乘和后乘的结果是不同的，使用时应结合具体情景分析使用。
//
// http://www.gcssloop.com/customview/Matrix_Basic



// camera 旋转中心默认是 原点(左上角 点)



