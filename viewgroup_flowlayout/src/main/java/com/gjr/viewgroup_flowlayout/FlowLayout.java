package com.gjr.viewgroup_flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geng
 * on 2017/9/1.
 */
public class FlowLayout extends ViewGroup {

    private List<Integer> levelNodes = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 测量下所有子View
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxChildrenWidth = 0;
        int maxChildrenHeight = getChildAt(0).getMeasuredHeight();
        int sum = 0;

        // 假设布局后，计算所有子View测量的 最大宽度和最大高度
        for (int i = 0; i < getChildCount(); i++) {
            int tmp = getChildAt(i).getMeasuredWidth();
            if (sum + tmp > widthSize) {
                maxChildrenWidth = sum;
                maxChildrenHeight += getChildAt(i).getMeasuredHeight();// 一般情况下，每个 item 高度都是相同的
                sum = tmp;
                levelNodes.add(i);
            } else {
                sum += tmp;
            }
        }

        // 根据测量模式，设置相应的 最大宽度和高度
        setMeasuredDimension(widthSpec == MeasureSpec.EXACTLY ? widthSize : maxChildrenWidth,
                heightSpec == MeasureSpec.EXACTLY ? heightSize : maxChildrenHeight);
    }


    /**
     * 为什么要重写 onLayout?
     * <p>
     * Called from layout when this view should
     * assign a size and position to each of its children.
     * <p>
     * Derived classes with children should override
     * this method and call layout on each of
     * their children.
     *
     * @param changed This is a new size or position for this view
     * @param l       Left position, relative to parent
     * @param t       Top position, relative to parent
     * @param r       Right position, relative to parent
     * @param b       Bottom position, relative to parent
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int deltaX = 0;
        int deltaY = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (levelNodes.contains(i)) {
                deltaX = 0;
                deltaY += v.getMeasuredHeight();
            }
            v.layout(deltaX, deltaY, deltaX + v.getMeasuredWidth(), deltaY + v.getMeasuredHeight());
            deltaX += v.getMeasuredWidth();
        }
    }


}
