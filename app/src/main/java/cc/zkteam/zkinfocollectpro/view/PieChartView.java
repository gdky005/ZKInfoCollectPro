package cc.zkteam.zkinfocollectpro.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by loong on 2017/12/20.
 */

public class PieChartView extends View {

    // 35cddd f6bd2e  0092d1

    private Paint circlePaint;

    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        circlePaint.setStrokeWidth(getWidth() / 4);
        RectF oval = new RectF(getWidth() / 8, getHeight() / 8, getWidth() * 7 / 8, getHeight() * 7 / 8);

        circlePaint.setColor(Color.parseColor("#35cddd"));
        canvas.drawArc(oval, -150, 120, false, circlePaint);
        circlePaint.setColor(Color.parseColor("#f6bd2e"));
        canvas.drawArc(oval, -30, 120, false, circlePaint);
        circlePaint.setColor(Color.parseColor("#0092d1"));
        canvas.drawArc(oval, 90, 120, false, circlePaint);
    }
}
