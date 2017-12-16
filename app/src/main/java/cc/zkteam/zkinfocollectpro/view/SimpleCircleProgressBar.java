package cc.zkteam.zkinfocollectpro.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import cc.zkteam.zkinfocollectpro.R;

/**
 * Created by loong on 2017/12/15.
 */

public class SimpleCircleProgressBar extends View {

    private static final int DEFAULT_THICKNESS = 10;
    private static final int DEFAULT_TEXT_SIZE = 30;
    private static final int DEFAULT_START_ANGLE = -90;
    private static final int DEFAULT_SWEEP_ANGLE = 360;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static final int DEFAULT_PROGRESS_BACKGROUND_COLOR = Color.GRAY;
    private static final int DEFAULT_PROGRESS_COLOR = Color.BLUE;


    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    private int thickness, textSize, startAngle, sweepAngle;
    private int textColor, backgroundColor, progressColor, progressBackgroundColor;

    private Paint textPaint, progressPaint;
    private int radius, centerPosition;


    public void setThickness(int thickness) {
        this.thickness = thickness;
        invalidate();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
        invalidate();
    }

    public void setSweepAngle(int sweepAngle) {
        this.sweepAngle = sweepAngle;
        invalidate();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        invalidate();
    }

    public void setProgressBackgroundColor(int progressBackgroundColor) {
        this.progressBackgroundColor = progressBackgroundColor;
        invalidate();
    }

    public SimpleCircleProgressBar(Context context) {
        this(context, null);
    }

    public SimpleCircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleCircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleCircleProgressBar);

        thickness = (int) typedArray.getDimension(R.styleable.SimpleCircleProgressBar_thickness, DEFAULT_THICKNESS);
        textSize = (int) typedArray.getDimension(R.styleable.SimpleCircleProgressBar_textSize, DEFAULT_TEXT_SIZE);
        startAngle = typedArray.getInteger(R.styleable.SimpleCircleProgressBar_startAngle, DEFAULT_START_ANGLE);
        sweepAngle = typedArray.getInteger(R.styleable.SimpleCircleProgressBar_sweepAngle, DEFAULT_SWEEP_ANGLE);
        textColor = typedArray.getColor(R.styleable.SimpleCircleProgressBar_textColor, DEFAULT_TEXT_COLOR);
        progressColor = typedArray.getColor(R.styleable.SimpleCircleProgressBar_progressColor, DEFAULT_PROGRESS_COLOR);
        progressBackgroundColor = typedArray.getColor(R.styleable.SimpleCircleProgressBar_progressBackGroundColor, DEFAULT_PROGRESS_BACKGROUND_COLOR);
        backgroundColor = typedArray.getColor(R.styleable.SimpleCircleProgressBar_backgroundColor, DEFAULT_BACKGROUND_COLOR);

        typedArray.recycle();

        initValue();
    }

    private void initValue() {

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeWidth(thickness);
        progressPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            radius = (DEFAULT_HEIGHT - thickness) / 2;
            centerPosition = DEFAULT_HEIGHT / 2;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            int size = Math.min(DEFAULT_WIDTH, measureHeight);
            setMeasuredDimension(size, size);
            radius = (size - thickness) / 2;
            centerPosition = size / 2;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            int size = Math.min(DEFAULT_HEIGHT, measureWidth);
            setMeasuredDimension(size, size);
            radius = (size - thickness) / 2;
            centerPosition = size / 2;
        } else {
            int size = Math.min(measureHeight, measureWidth);
            setMeasuredDimension(size, size);
            radius = (size - thickness) / 2;
            centerPosition = size / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (sweepAngle < 0) sweepAngle = 0;
        sweepAngle = sweepAngle % 360;

        canvas.drawColor(backgroundColor);
        progressPaint.setColor(progressBackgroundColor);
        canvas.drawCircle(centerPosition, centerPosition, radius, progressPaint);
        progressPaint.setColor(progressColor);
        RectF rect = new RectF(thickness / 2, thickness / 2, getWidth() - thickness / 2, getHeight() - thickness / 2);
        canvas.drawArc(rect, startAngle, sweepAngle, false, progressPaint);

        float data = sweepAngle / 360.0f;

        String text = String.valueOf((int) (data * 100)) + "%";
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float ascent = fontMetrics.ascent;
        float descent = fontMetrics.descent;
        float width = textPaint.measureText(text);

        canvas.drawText(text, (getWidth() - width) / 2, (getHeight() - (descent - ascent)) / 2 - ascent, textPaint);
    }
}