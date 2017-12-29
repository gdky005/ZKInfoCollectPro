package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.zkinfocollectpro.R;

/**
 * ZKKeyValueLayout 自动扩展布局
 *
 * 主要作用是将 key:value 的数据 用 ZKKeyValueFiledView 分两列动态计算并显示出来。
 *
 * Created by wangqing on 2017/12/26.
 */
public class ZKKeyValueLayout extends ZKBaseView implements IZKResult<List<String>>{

    private List<ZKKeyValueFiledView> zkKeyValueFiledViewList = new ArrayList<>();

    public ZKKeyValueLayout(Context context) {
        super(context);
    }

    public ZKKeyValueLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKKeyValueLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        setOrientation(VERTICAL);

        ZKKeyValueFiledView rightZkFiledView = null;

        for (int i = 0; i < jsonArray.length(); i++) {
            // 获取到存储起来的一组键值对
            JSONObject object = jsonArray.optJSONObject(i);
            String key = object.names().optString(0);
            String value = object.optString(key);

            // 根据数据个数分两列显示数据
            if (i % 2 == 0) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.kind_layout_zk_key_value_layout, null);

                ZKKeyValueFiledView leftZkFiledView = view.findViewById(R.id.left_zk_filed_view);
                rightZkFiledView = view.findViewById(R.id.right_zk_filed_view);
                leftZkFiledView.setKeyValue(key, value);

                rightZkFiledView.setVisibility(GONE);


                zkKeyValueFiledViewList.add(leftZkFiledView);
                LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                addView(view, layoutParams);
            } else {
                if (rightZkFiledView != null) {
                    rightZkFiledView.setVisibility(VISIBLE);
                    rightZkFiledView.setKeyValue(key, value);
                    zkKeyValueFiledViewList.add(rightZkFiledView);
                }
            }
        }
    }

    @Override
    public List<String> getResult() {
        List<String> resultList = new ArrayList<>();

        for (ZKKeyValueFiledView zkFiled :
                zkKeyValueFiledViewList) {
            resultList.add(zkFiled.getResult());
        }

        return resultList;
    }
}
