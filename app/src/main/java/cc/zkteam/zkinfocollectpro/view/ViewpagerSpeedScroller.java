package cc.zkteam.zkinfocollectpro.view;

/**
 * Created by Administrator on 2017/12/26 0026.
 */


import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class ViewpagerSpeedScroller extends Scroller {
    private int mDuration = 0;

    public ViewpagerSpeedScroller(Context context) {
        super(context);
    }

    public ViewpagerSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ViewpagerSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }


    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }


    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
