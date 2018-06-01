package com.example.geng.transitionscaleviewdemo

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bytedance.common.utility.UIUtils
import kotlin.math.abs
import kotlin.math.sqrt


class TransitionScaleView : FrameLayout {

    private val VELOCITY_MIN = 600f
    private val VELOCITY_MAX = 1800f
    private val MIN_SCROLL = 400f

    private val LAYER_FLAGS = (Canvas.MATRIX_SAVE_FLAG or Canvas.CLIP_SAVE_FLAG
            or Canvas.HAS_ALPHA_LAYER_SAVE_FLAG or Canvas.FULL_COLOR_LAYER_SAVE_FLAG
            or Canvas.CLIP_TO_LAYER_SAVE_FLAG)

    private val scrrenW = UIUtils.getScreenWidth(context).toFloat()
    private val scrrenH = UIUtils.getScreenHeight(context).toFloat()

    private val originRadius = sqrt(scrrenH * scrrenH + scrrenW * scrrenW / 4)

    private var radius = originRadius
    private var mCurrentY = 0f
    private var mIsCurrentOverMaxVelocity = false
    private var mLastVelocity = 0f

    private var mVelocityTracker: VelocityTracker? = null

    private var mPaint = Paint()
    private lateinit var xfermode: PorterDuffXfermode


    private var mContentView: View? = null
    private var mChild: View? = null


    private var mCallBack: AnimationCallBack? = null


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        init()
    }

    private fun init() {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.RED
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        // 速度监控
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker!!.addMovement(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                fun1(event)
            }
            MotionEvent.ACTION_MOVE -> {
                fun2(event)
            }
            MotionEvent.ACTION_UP -> {
                fun3(event)
            }
        }
        return true
    }

    override fun dispatchDraw(canvas: Canvas) {


        canvas.save();
        val path = Path()
        path.addCircle(scrrenW / 2, scrrenH, radius, Path.Direction.CCW);
        canvas.clipPath(path, Region.Op.INTERSECT);
        canvas.drawColor(Color.RED)
        canvas.restore();

        super.dispatchDraw(canvas)

    }

    fun fun1(event: MotionEvent) {
        mCurrentY = event.y
        mIsCurrentOverMaxVelocity = false
        mLastVelocity = 0f
    }

    fun fun2(event: MotionEvent) {
        val deltaY = event.y - mCurrentY
        mVelocityTracker!!.computeCurrentVelocity(500)// 计算500ms滑动的距离
        val velocityY = mVelocityTracker!!.yVelocity
        mLastVelocity = velocityY

        mIsCurrentOverMaxVelocity = abs(velocityY) >= VELOCITY_MAX


        println("velocityY: $velocityY")

        if (abs(velocityY) > VELOCITY_MAX && abs(deltaY) >= MIN_SCROLL) {
            // 完成所有动画
            if (velocityY > 0) {
                convergenceAnimation()
            } else {
                spreadAnimation()
            }
            return
        } else {
            // 逐渐移动
            radius -= deltaY
        }

        if (radius > originRadius) {
            radius = originRadius
        }

        println("move radius: $deltaY - $radius")
        invalidate()
        mCurrentY = event.y
    }


    fun fun3(event: MotionEvent) {
        if (mIsCurrentOverMaxVelocity) {
            if (mLastVelocity > 0 && radius < 5 * scrrenH / 6) {
                convergenceAnimation()
            } else {
                spreadAnimation()
            }
        }

        // 判断半径大小，决定动画快慢
        if (mLastVelocity > 0) {
            if (radius < 5 * scrrenH / 6) {
                convergenceAnimation()
            } else {
                spreadAnimation()
            }
            return
        } else {
            spreadAnimation()
        }
    }


    fun convergenceAnimation() {
        val valueAnimator = ValueAnimator.ofFloat(1f, 0f)
        valueAnimator.duration = 500
        valueAnimator.addUpdateListener { animation ->
            println("动画 ： ${animation.animatedValue as Float}")
            radius *= animation.animatedValue as Float
            invalidate()
            if (animation.animatedValue as Float == 0f) {
                mCallBack?.onEnd()
            }
        }
        valueAnimator.start()
    }

    fun spreadAnimation() {
        val valueAnimator = ValueAnimator.ofFloat(1f, 5f)
        valueAnimator.duration = 500
        valueAnimator.addUpdateListener { animation ->
            println("动画 ： ${animation.animatedValue as Float}")
            val tmp = radius * animation.animatedValue as Float
            if (tmp > originRadius) {
                radius = originRadius
            } else {
                radius = tmp
            }
            invalidate()
        }
        valueAnimator.start()
    }

    interface AnimationCallBack {
        fun onEnd()
    }

    fun addAnimationCallBack(callBack: AnimationCallBack) {
        mCallBack = callBack
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mVelocityTracker?.recycle()
        mVelocityTracker = null;
    }


    fun attachToActivity(activity: Activity) {
        val a = activity.theme.obtainStyledAttributes(
                intArrayOf(android.R.attr.windowBackground))
        val background = a.getResourceId(0, 0)
        a.recycle()

        val decor = activity.window.decorView
        if (decor is ViewGroup) {
            // http://slardar.byted.org/page/abnormal/trace?did=35065813607 这里偶现 crash
            val decorChild = decor.getChildAt(0)
            decorChild.setBackgroundResource(background)
            decor.removeView(decorChild)
            addView(decorChild)
            setContentView(decorChild)
            decor.addView(this)
        } else {
        }
    }

    private fun setContentView(decorChild: View) {
        mChild = decorChild
        mContentView = decorChild.parent as View
    }
}