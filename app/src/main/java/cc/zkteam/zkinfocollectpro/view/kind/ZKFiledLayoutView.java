package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.zkteam.zkinfocollectpro.R;

/**
 * ZKFiledLayoutView 自动扩展布局
 * Created by wangqing on 2017/12/26.
 */

public class ZKFiledLayoutView extends ZKBaseView {


    public ZKFiledLayoutView(Context context) {
        super(context);
    }

    public ZKFiledLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKFiledLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(View rootView) {

    }


    public void setJsonArray(JSONArray jsonArray) {

        ZKKeyValueFiledView rightZkFiledView = null;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.optJSONObject(i);
            String key = object.names().optString(0);
            String value = object.optString(key);

            if (i % 2 == 0) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.kind_layout_view, null);

                ZKKeyValueFiledView leftZkFiledView = view.findViewById(R.id.left_zk_filed_view);
                rightZkFiledView = view.findViewById(R.id.right_zk_filed_view);
                leftZkFiledView.setKeyValue(key, value);

                rightZkFiledView.setVisibility(GONE);

                setOrientation(VERTICAL);
                LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                addView(view, layoutParams);
            } else {
                if (rightZkFiledView != null) {
                    rightZkFiledView.setVisibility(VISIBLE);
                    rightZkFiledView.setKeyValue(key, value);
                }
            }
        }
    }
}
