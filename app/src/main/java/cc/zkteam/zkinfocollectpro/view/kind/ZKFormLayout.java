package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ZKFormView 自动扩展布局
 * <p>
 * 将 一组 ZKFiled 添加到 ZKFormView 中，可以更快速，方便的布局，无需一个个在布局里面写，更加简洁化。
 * 将该组所有的数据当做一个表单处理。 默认是 edittext 类型，其他类型可以通过在 JSONArray 中设置。
 * <p>
 * Created by wangqing on 2017/12/26.
 */

public class ZKFormLayout extends ZKBaseView implements IZKResult<List<String>> {

    private List<ZKFiled> zkFiledList = new ArrayList<>();

    public ZKFormLayout(Context context) {
        super(context);
    }

    public ZKFormLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKFormLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        setJsonArray(jsonArray, null);
    }

    public void setJsonArray(JSONArray jsonArray, Map<Integer, Object> map) {
        setOrientation(VERTICAL);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.optJSONObject(i);
            if (object != null) {
                String key = object.names().optString(0);
                Object value = object.optString(key);

                ZKFiled zkFiled = new ZKFiled(getContext());
                int type = ZKFiled.TYPE_FILED_FORM_EDIT_TEXT;

                if (map != null && map.size() > 0) {
                    Object obj = map.get(i);

                    if (obj instanceof ArrayList) {
                        ArrayList list = (ArrayList) obj;
                        type = (int) list.get(0);
                        value = list.get(1);
                    } else if (obj instanceof Integer) {
                        type = (Integer) obj;
                    }
                }

                zkFiled.setData(String.valueOf(i), key, value, i, type);

                zkFiledList.add(zkFiled);
                addView(zkFiled);
            }
        }
    }

    @Override
    public List<String> getResult() {
        List<String> resultList = new ArrayList<>();

        for (ZKFiled zkFiled :
                zkFiledList) {
            resultList.add(zkFiled.getResult());
        }

        return resultList;
    }

}
