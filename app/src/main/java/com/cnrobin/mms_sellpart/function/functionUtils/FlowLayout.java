package com.cnrobin.mms_sellpart.function.functionUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.cnrobin.mms_sellpart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cnrobin on 17-11-29.
 * Just Enjoy It!!!
 */

public class FlowLayout extends ViewGroup {
    int childCount;
    private int widthSpace;
    private int heightSpace;
    private int mMaxWidth;
    private List<Line> lines = new ArrayList<>();
    private Line mCurrentLine;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        widthSpace = (int) array.getDimension(R.styleable.FlowLayout_widthSpace, 5);
        heightSpace = (int) array.getDimension(R.styleable.FlowLayout_heightSpace, 5);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        lines.clear();
        mCurrentLine = null;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        mMaxWidth = width - getPaddingLeft() - getPaddingRight();
        childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            if (mCurrentLine == null) {
                mCurrentLine = new Line(mMaxWidth, widthSpace);
                mCurrentLine.addView(childView);
                lines.add(mCurrentLine);
            } else {
                if (mCurrentLine.canAddView(childView)) {
                    mCurrentLine.addView(childView);
                } else {
                    mCurrentLine = new Line(mMaxWidth, widthSpace);
                    mCurrentLine.addView(childView);
                    lines.add(mCurrentLine);
                }
            }
        }
        int height = getPaddingBottom() + getPaddingTop();
        for (int i = 0; i < lines.size(); i++) {
            height += lines.get(i).height;
        }
        height += (lines.size() - 1) * heightSpace;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        l = getPaddingLeft();
        t = getPaddingTop();
        int count = 0;
        for (Line line : lines
                ) {
            line.layout(t, l);
            t += line.height;
            if (count != lines.size() - 1) {
                t += heightSpace;
            }
            count++;
        }
    }

    static class Line {
        private int height;
        private int maxWidth;
        private int horizontalSpace;
        private int usedWidth;
        private List<View> views = new ArrayList<>();

        public Line(int maxWidth, int horizontalSpace) {
            this.maxWidth = maxWidth;
            this.horizontalSpace = horizontalSpace;
        }

        public void addView(View view) {
            int childWidth = view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();
            if (views.size() == 0) {
                if (childWidth > maxWidth) {
                    usedWidth = maxWidth;
                    height = childHeight;
                } else {
                    usedWidth = childWidth;
                    height = childHeight;
                }
            } else {
                usedWidth += childWidth + horizontalSpace;
                height = (childHeight > height) ? childHeight : height;
            }
            views.add(view);
        }

        public boolean canAddView(View view) {
            if (views.size() == 0) {
                return true;
            }
            if (view.getMeasuredWidth() > (maxWidth - usedWidth - horizontalSpace)) {
                return false;
            }
            return true;
        }

        public void layout(int t, int l) {
            int avg = (maxWidth - usedWidth) / views.size();
            for (View view : views
                    ) {
                int measuredWidth = view.getMeasuredWidth();
                int measuredHeight = view.getMeasuredHeight();
                view.measure(MeasureSpec.makeMeasureSpec(measuredWidth + avg, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
                measuredWidth = view.getMeasuredWidth();
                int top = t;
                int left = l;
                int right = measuredWidth + left;
                int bottom = measuredHeight + top;
                view.layout(left, top, right, bottom);
                l += measuredWidth + horizontalSpace;
            }

        }

    }
}
