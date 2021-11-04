package com.cnrobin.mms_sellpart.function.functionUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.cnrobin.mms_sellpart.R;

/**
 * Created by cnrobin on 17-11-15.
 * Just Enjoy It!!!
 */

public class StatisticsView extends View {
    private Paint mBorderPaint;
    private Paint circlePaint;
    private Paint mPathPaint;
    private Path mPath;
    private int maxValue = 100;
    private int deviderCount = 10;
    private String title;
    private int perValue = maxValue / deviderCount;
    private String[] bottomStr = {};
    private float[] values = {};
    private float bottomGap;
    private float leftGap;
    private TextPaint textPaint;
    private int dividerCount;

    public StatisticsView(Context context) {
        super(context);
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StatisticsView);
        maxValue = array.getInt(R.styleable.StatisticsView_maxValue, 100);
        dividerCount = array.getInt(R.styleable.StatisticsView_dividerCount, 10);
        title = array.getString(R.styleable.StatisticsView_title);
        int lineColor = array.getColor(R.styleable.StatisticsView_lineColor, Color.BLACK);
        int textColor = array.getColor(R.styleable.StatisticsView_textColor, Color.BLACK);
        mBorderPaint = new Paint();
        circlePaint = new Paint();
        mPathPaint = new Paint();
        title = "最近五日销售情况";
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(lineColor);
        mBorderPaint.setStrokeWidth(1);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        mPathPaint.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(3);

        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(DensityUtil.dip2px(getContext(), 12));
        mPath = new Path();

        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        array.recycle();
    }

    public void setBottomStr(String[] bottomStr) {
        this.bottomStr = bottomStr;
        requestLayout();
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMeasureSpec == MeasureSpec.EXACTLY && heighMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heighSize);
        } else if (widthMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, widthSize);
        } else if (heighMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(heighSize, heighSize);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        bottomGap = getWidth() / (bottomStr.length + 1);
        leftGap = getHeight() / (deviderCount + 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bottomStr == null || bottomStr.length == 0) {
            return;
        }
        canvas.drawLine(bottomGap, getHeight() - leftGap, bottomGap, leftGap, mBorderPaint);
        float fontHeight = textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent;
        canvas.drawLine(bottomGap, getHeight() - leftGap, getWidth() - bottomGap, getHeight() - leftGap, mBorderPaint);
        for (int i = 1; i <= bottomStr.length; i++) {
            canvas.drawCircle(i * bottomGap, getHeight() - leftGap, 6, circlePaint);
            canvas.drawText(bottomStr[i - 1], i * bottomGap - textPaint.measureText(bottomStr[i - 1]) / 2, getHeight() - leftGap / 2 + fontHeight / 2, textPaint);
        }
        canvas.drawText(title, bottomGap, leftGap / 2, textPaint);
        for (int i = 1; i <= deviderCount + 1; i++) {
            canvas.drawText(perValue * (i - 1) + "", bottomGap / 2 - (textPaint.measureText(perValue * (i - 1) + "") / 2), (((dividerCount + 2 - i))) * leftGap + fontHeight / 2, textPaint);
            canvas.drawLine(bottomGap, getHeight() - ((i) * leftGap), getWidth() - bottomGap, getHeight() - ((i) * leftGap), mBorderPaint);
        }
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                mPath.moveTo(bottomGap, (dividerCount + 1) * leftGap - (values[i] * leftGap / perValue));
            } else {
                mPath.lineTo((i + 1) * bottomGap, (dividerCount + 1) * leftGap - (values[i] * leftGap / perValue));
            }
            canvas.drawCircle((i + 1) * bottomGap, (dividerCount + 1) * leftGap - (values[i] * leftGap / perValue), 6, circlePaint);
        }
        canvas.drawPath(mPath, mPathPaint);

    }
}

