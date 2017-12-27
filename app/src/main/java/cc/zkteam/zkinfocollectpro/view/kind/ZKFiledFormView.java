package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * ZKFiledFormView 自动扩展布局
 * Created by wangqing on 2017/12/26.
 */

public class ZKFiledFormView extends LinearLayout {

    public ZKFiledFormView(Context context) {
        super(context);
        init();
    }

    public ZKFiledFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZKFiledFormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void setJsonArray(JSONArray jsonArray) {
        setJsonArray(jsonArray, null);
    }

    public void setJsonArray(JSONArray jsonArray, Map<Integer, Integer> map) {
        setOrientation(VERTICAL);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.optJSONObject(i);
            String key = object.names().optString(0);
            String value = object.optString(key);

            ZKEditFiledView zkEditFiledView = new ZKEditFiledView(getContext());
            int type = ZKEditFiledView.TYPE_FILED_FORM_EDIT_TEXT;

            if (map != null && map.size() > 0) {
                Integer integer = map.get(i);
                if (integer !=  null) {
                    type = integer;
                }
            }

            zkEditFiledView.setData(String.valueOf(i), key, value, i, type);

            addView(zkEditFiledView);

//            if (i % 2 == 0) {
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                View view = inflater.inflate(R.layout.kind_layout_view, null);
//
//                ZKFiledView leftZkFiledView = view.findViewById(R.id.left_zk_filed_view);
//                rightZkFiledView = view.findViewById(R.id.right_zk_filed_view);
//                leftZkFiledView.setKeyValue(key, value);
//
//                rightZkFiledView.setVisibility(GONE);
//
//                setOrientation(VERTICAL);
//                LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                addView(view, layoutParams);
//            } else {
//                if (rightZkFiledView != null) {
//                    rightZkFiledView.setVisibility(VISIBLE);
//                    rightZkFiledView.setKeyValue(key, value);
//                }
//            }
        }
    }
}