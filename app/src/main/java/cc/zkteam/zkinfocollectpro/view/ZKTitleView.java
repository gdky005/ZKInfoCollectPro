package cc.zkteam.zkinfocollectpro.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.zkteam.zkinfocollectpro.R;

/**
 * 通用的标题View
 * ZKTitleView
 * Created by WangQing on 2017/12/24.
 */

public class ZKTitleView extends LinearLayout {

    public Toolbar toolbar;
    public TextView centerTextTV;
    public ImageView leftIV;
    public ImageView rightIV;

    public ZKTitleView(Context context) {
        super(context);
        init();
    }

    public ZKTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZKTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.common_title, null);

        toolbar = view.findViewById(R.id.common_title_toolbar);
        centerTextTV = view.findViewById(R.id.common_title_tv_text);
        leftIV = view.findViewById(R.id.common_title_iv_left);
        rightIV = view.findViewById(R.id.common_title_iv_right);

        addView(view);
    }

    public ImageView titleLeftImageView() {
        return leftIV;
    }

    public ImageView titleRightImageView() {
        return leftIV;
    }

    public TextView titleCenterTextView() {
        return centerTextTV;
    }

    public void setLeftIVSrc(@DrawableRes int id) {
        leftIV.setImageResource(id);
    }

    public void setCenterTVText(@StringRes int string) {
        setCenterTVText(getContext().getString(string));
    }

    public void setCenterTVText(String title) {
        centerTextTV.setText(title);
    }

    public void setRightIVSrc(@DrawableRes int id) {
        rightIV.setImageResource(id);
    }

}
