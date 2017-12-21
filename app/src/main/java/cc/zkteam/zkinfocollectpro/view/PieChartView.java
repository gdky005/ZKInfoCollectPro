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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.RED);
        RectF oval = new RectF(0, 0, getWidth(), getHeight());

//        circlePaint.setColor(Color.RED);
//        circlePaint.setStrokeWidth(20);
//        circlePaint.setColor(Color.parseColor("#35cddd"));
//        canvas.drawArc(oval, 60, 180, false, circlePaint);
//        circlePaint.setColor(Color.parseColor("##f6bd2e"));
//        canvas.drawArc(oval, 180, 300, false, circlePaint);
//        circlePaint.setColor(Color.parseColor("#0092d1"));
//        canvas.drawArc(oval, -60, 60, false, circlePaint);
    }
}
