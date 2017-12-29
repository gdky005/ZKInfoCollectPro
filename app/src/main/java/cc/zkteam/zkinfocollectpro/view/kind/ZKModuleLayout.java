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
 * ZKModuleLayout
 * Created by WangQing on 2017/12/29.
 */

public class ZKModuleLayout extends ZKBaseView implements IZKResult {

    private List<ZKFiled> zkFiledList = new ArrayList<>();

    public ZKModuleLayout(Context context) {
        super(context);
    }

    public ZKModuleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKModuleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(View rootView) {

    }

    public void setJsonArray(JSONArray jsonArray, Map<Integer, Object> map, List tileList) {
        setOrientation(VERTICAL);

        try {
            ZKKindTitle zkKindTitle = new ZKKindTitle(context);
            String name = String.valueOf(tileList.get(0));
            int type = (int) tileList.get(1);
            String[] strings = (String[]) tileList.get(2);

            zkKindTitle.setData(name, type, strings);
            addView(zkKindTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
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
