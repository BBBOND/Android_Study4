package com.kim.flowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 流视图
 * Created by 伟阳 on 2016/2/11.
 */
public class FlowLayout extends ViewGroup {
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
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        //每一行的宽高
        int lineWidth = 0;
        int lineHeight = 0;

        //得到内部元素的个数
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //测量子View的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到子View的LayoutParams
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            //子View占据的宽度
            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            //子View占据的高度
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            //换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //对比得到最大宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                lineWidth = childWidth;
                //记录行高
                height += lineHeight;
            } else {
                //叠加行宽
                lineWidth += childWidth;
                //得到当前行最大的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }

            //最后一个控件
            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        //确定的dp,match_parent  EXACTLY
        //要多大有多大  UNSPCIFIED
        //wrap_content AT_MOST
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 存储所有view
     */
    private List<List<View>> allViews = new ArrayList<>();
    /**
     * 存储每一行高度
     */
    private List<Integer> lineHeights = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        allViews.clear();
        lineHeights.clear();
        //当前viewGroup的宽度
        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<>();
        int childCount = getChildCount();
        //将子View放入list分行进行存储
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            //如果换行进行存储每一行的子view和行高
            if (childWidth + lineWidth + layoutParams.leftMargin + layoutParams.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                lineHeights.add(lineHeight);
                allViews.add(lineViews);

                lineWidth = 0;
                lineHeight = childHeight + layoutParams.topMargin + layoutParams.bottomMargin;

                lineViews = new ArrayList<>();
            }

            lineWidth += childWidth + layoutParams.leftMargin + layoutParams.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + layoutParams.topMargin + layoutParams.bottomMargin);
            lineViews.add(child);
        }
        //处理最后一行
        lineHeights.add(lineHeight);
        allViews.add(lineViews);

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineNum = allViews.size();

        //设置子View的布局
        for (int i = 0; i < lineNum; i++) {
            lineViews = allViews.get(i);
            lineHeight = lineHeights.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == GONE) {
                    continue;
                }

                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + layoutParams.leftMargin;
                int tc = top + layoutParams.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                //为子View设置布局
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    /**
     * 与当前ViewGroup对应的LayoutParams
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
