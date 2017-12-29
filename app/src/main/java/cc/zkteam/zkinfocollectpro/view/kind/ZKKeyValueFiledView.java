package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import cc.zkteam.zkinfocollectpro.R;

/**
 * ZKFiledView
 * Created by wangqing on 2017/12/26.
 */

public class ZKKeyValueFiledView extends ZKBaseView {

    TextView keyFiled;
    TextView valueFiled;

    String key;
    String value;

    public ZKKeyValueFiledView(Context context) {
        super(context);
    }

    public ZKKeyValueFiledView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKKeyValueFiledView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.kind_layout_key_value_field;
    }

    @Override
    protected void initViews(View rootView) {
        keyFiled = findView(R.id.text_filed_key);
        valueFiled = findView(R.id.right_layout_text_filed_value);
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
        setViewText(keyFiled, k);
        setViewText(valueFiled, v);
    }
}
