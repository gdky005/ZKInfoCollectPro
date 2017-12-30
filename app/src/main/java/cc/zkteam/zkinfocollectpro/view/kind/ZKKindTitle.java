package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cc.zkteam.zkinfocollectpro.R;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * ZKKindTitle
 * 主要是各种大标题的几种类型
 * Created by wangqing on 2017/12/27.
 */

public class ZKKindTitle extends ZKBaseView implements IZKResult {

    private int type;

    private TextView titleNameTV;

    private TextView zkTitleKindRightSelectBtn;
    private TextView zkTitleKindRightCheckBtn;

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
        setData(name, TYPE_BUTTON, null);
    }

    public void setSingleSelectTitle(String name, String[] defaultText) {
        setData(name, TYPE_SINGLE_SELECT, defaultText);
    }

    /**
     * 请使用 setSingleSelectTitle(String name, String[] defaultText)
     */
    @Deprecated
    public void setSingleSelectTitle(String name, String defaultText) {
        setData(name, TYPE_SINGLE_SELECT, defaultText);
    }

    public void setData(String name, @KindTitleType int kindTitleType, Object defaultText) {

        this.type = kindTitleType;
        updateView(name, kindTitleType, defaultText);
    }

    private void updateView(String name, @KindTitleType int kindTitleType, Object defaultValue) {

        setViewText(titleNameTV, name);

        switch (kindTitleType) {
            case TYPE_NONE:
                break;
            case TYPE_BUTTON:
                View zkTitleKindRightCheckBtnLayout = ((ViewStub) findView(R.id.zk_title_kind_right_check_btn_layout)).inflate();
                zkTitleKindRightCheckBtn = zkTitleKindRightCheckBtnLayout.findViewById(R.id.zk_title_kind_right_check_btn);
                zkTitleKindRightCheckBtn.setOnClickListener(v ->
                        ToastUtils.showShort("你点击了查询按钮，这里应该 去访问服务器接口哦，然后处理逻辑"));

                break;
            case TYPE_SINGLE_SELECT:
                if (defaultValue instanceof String[]) {
                    String[] value = (String[]) defaultValue;
                    if (value.length > 0) {
                        View zkTitleKindRightSelectBtnLayout = ((ViewStub) findView(R.id.zk_title_kind_right_select_btn_layout)).inflate();
                        zkTitleKindRightSelectBtn = zkTitleKindRightSelectBtnLayout.findViewById(R.id.zk_title_kind_right_select_btn);
                        zkTitleKindRightSelectBtn.setOnClickListener(view -> {
                            TextView btn = (TextView) view;
                            OptionPicker picker3 = new OptionPicker(activity, value);
                            picker3.setCanceledOnTouchOutside(false);
                            picker3.setDividerRatio(WheelView.DividerConfig.FILL);
                            picker3.setShadowColor(Color.BLUE, 40);
                            picker3.setSelectedIndex(0);
                            picker3.setCycleDisable(true);
                            picker3.setTextSize(20);
                            picker3.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                                @Override
                                public void onOptionPicked(int index1, String item) {
                                    btn.setText(item);
                                }
                            });
                            picker3.show();
                        });
                        zkTitleKindRightSelectBtn.setText(value[0]);
                    }
                }
                break;
        }
    }

    @Override
    public String getResult() {
        switch (type) {
            case TYPE_NONE:
                break;
            case TYPE_SINGLE_SELECT:
                CharSequence charSequence = zkTitleKindRightSelectBtn.getText();
                return charSequence.toString();
            case TYPE_BUTTON:
                break;
        }
        return "";
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
