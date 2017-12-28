package cc.zkteam.zkinfocollectpro.view.kind;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.zkteam.zkinfocollectpro.R;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * ZKKindTitle
 * Created by wangqing on 2017/12/27.
 */

public class ZKKindTitle extends LinearLayout {

    private TextView titleNameTV;
    private Button rightSelectBtn;

    private String titleName;
    private String defaultText;

    private Activity activity;
    private Context context;

    public ZKKindTitle(Context context) {
        super(context);
        init(context);
    }

    public ZKKindTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZKKindTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        if (context instanceof Activity)
            this.activity = (Activity) context;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.kind_layout_zk_title, null);
        titleNameTV = view.findViewById(R.id.zk_title_kind_tv);
        rightSelectBtn = view.findViewById(R.id.zk_title_kind_right_select_btn);

        addView(view);
    }

    public void setData(String name, String defaultText) {
        this.titleName = name;
        this.defaultText = defaultText;

        updateView(name, defaultText);
    }

    private void updateView(String name, String defaultText){
        if (name != null && titleNameTV != null && !titleNameTV.getText().equals(name)) {
            titleNameTV.setText(name);
        }

        if (defaultText != null && rightSelectBtn != null && !rightSelectBtn.getText().equals(name)) {
            rightSelectBtn.setVisibility(VISIBLE);
            rightSelectBtn.setText(defaultText);
            rightSelectBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button btn = (Button) view;
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
        } else if (defaultText == null && rightSelectBtn != null) {
            rightSelectBtn.setVisibility(INVISIBLE);
        }



    }

}
