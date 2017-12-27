package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.zkteam.zkinfocollectpro.R;

/**
 * ZKFiledView
 * Created by wangqing on 2017/12/26.
 */

public class ZKFiledView extends LinearLayout {

    TextView keyFiled;
    TextView valueFiled;

    String key;
    String value;


    public ZKFiledView(Context context) {
        super(context);
        init();
    }

    public ZKFiledView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZKFiledView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.kind_layout_text_field, null);

        keyFiled = view.findViewById(R.id.text_filed_key);
        valueFiled = view.findViewById(R.id.right_layout_text_filed_value);
        addView(view);
    }

    public void setKey(String key) {
        this.key = key;
        updateView(key, null);
    }

    public void setValue(String value) {
        this.value = value;
        updateView(null, value);
    }

    public void setKeyValue(String key, String value) {
        this.key = key;
        this.value = value;
        updateView(key, value);
    }

    private void updateView(String k, String v){
        if (k != null && keyFiled != null && !keyFiled.getText().equals(k)) {
            keyFiled.setText(k);
        }

        if (v != null && valueFiled != null && !valueFiled.getText().equals(v)) {
            valueFiled.setText(v);
        }
    }
}
