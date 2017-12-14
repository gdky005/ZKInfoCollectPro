package cc.zkteam.zkinfocollectpro.view;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * ZKImageView
 * Created by WangQing on 2017/12/14.
 */

public class ZKImageView extends SimpleDraweeView {
    public ZKImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ZKImageView(Context context) {
        super(context);
    }

    public ZKImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ZKImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
