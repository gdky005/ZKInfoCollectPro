package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cc.zkteam.zkinfocollectpro.R;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * ZKKindTitle
 * Created by wangqing on 2017/12/27.
 */

public class ZKKindTitle extends ZKBaseView {

    private TextView titleNameTV;
    private TextView rightSelectTV;
    private TextView rightCheckTV;

    public ZKKindTitle(Context context) {
        super(context);
    }

    public ZKKindTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKKindTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.kind_layout_zk_title;
    }

    @Override
    protected void initViews(View rootView) {
        titleNameTV = findView(R.id.zk_title_kind_tv);
        rightSelectTV = findView(R.id.zk_title_kind_right_select_btn);
        rightCheckTV = findView(R.id.zk_title_kind_right_check_btn);
    }

    /**
     * 纯文本类型
     */
    public void setTextTitle(String name) {
        setData(name, TYPE_NONE, null);
    }

    /**
     * Button 类型
     */
    public void setButtonTitle(String name) {
        setButtonTitle(name, "否");
    }

    /**
     * Button 类型
     */
    public void setButtonTitle(String name, String defaultText) {
        setData(name, TYPE_BUTTON, defaultText);
    }

    public void setSingleSelectTitle(String name, String defaultText) {
        setData(name, TYPE_SINGLE_SELECT, defaultText);
    }

    public void setData(String name, @KindTitleType int kindTitleType, String defaultText) {
        updateView(name, kindTitleType, defaultText);
    }

    private void updateView(String name, @KindTitleType int kindTitleType, String defaultText) {

        switch (kindTitleType) {
            case TYPE_NONE:
                rightCheckTV.setVisibility(GONE);
                rightSelectTV.setVisibility(GONE);
                break;
            case TYPE_BUTTON:
                rightCheckTV.setVisibility(VISIBLE);
                rightSelectTV.setVisibility(GONE);
                break;
            case TYPE_SINGLE_SELECT:
                rightCheckTV.setVisibility(GONE);
                rightSelectTV.setVisibility(VISIBLE);
                break;
        }

        setViewText(titleNameTV, name);
        setViewText(rightCheckTV, defaultText);
        setViewText(rightSelectTV, defaultText);

        if (rightSelectTV != null) {
            rightSelectTV.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView btn = (TextView) view;
                    OptionPicker picker3 = new OptionPicker(activity, new String[]{
                            "第一单位", "第二单位", "第三单位"
                    });
                    picker3.setCanceledOnTouchOutside(false);
                    picker3.setDividerRatio(WheelView.DividerConfig.FILL);
                    picker3.setShadowColor(Color.BLUE, 40);
                    picker3.setSelectedIndex(1);
                    picker3.setCycleDisable(true);
                    picker3.setTextSize(20);
                    picker3.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            btn.setText(item);
                        }
                    });
                    picker3.show();
                }
            });

        }

        if (rightCheckTV != null) {
            rightCheckTV.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort("你点击了查询按钮，这里应该 去访问服务器接口哦，然后处理逻辑");
                }
            });
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_NONE, TYPE_SINGLE_SELECT, TYPE_BUTTON})
    public @interface KindTitleType {
        int key() default TYPE_NONE;
    }

    public static final int TYPE_NONE = 1;
    public static final int TYPE_SINGLE_SELECT = 2;
    public static final int TYPE_BUTTON = 3;

    @KindTitleType
    int key;

    @KindTitleType
    public int getConstant() {
        return key;
    }

    public void setConstant(@KindTitleType int key) {
        this.key = key;
    }

}
