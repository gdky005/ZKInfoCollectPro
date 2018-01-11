package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.zkteam.zkinfocollectpro.bean.ZK31Bean;

/**
 * Created by WangQing on 2018/1/3.
 */

public class ZKModuleListLayout extends ZKBaseView implements IZKResult<JSONObject> {

    private boolean isMoreData;
    private ArrayList<ZKModuleLayout> zkModuleLayouts = new ArrayList<>();

    public ZKModuleListLayout(Context context) {
        super(context);
    }

    public ZKModuleListLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKModuleListLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(View rootView) {

    }

    public void setMoreData(boolean moreData) {
        isMoreData = moreData;
    }

    public void setDataBeanList(List<ZK31Bean.DataBeanX> dataBeanList) {
        setOrientation(VERTICAL);

        if (dataBeanList == null)
            return;

        for (ZK31Bean.DataBeanX dataBeanX : dataBeanList) {
            JSONArray moduleArray = new JSONArray();
            Map<Integer, Object> objectHashMap = new HashMap<>();

            ZKModuleLayout zkModuleLayout = new ZKModuleLayout(context);

            List<ZK31Bean.DataBeanX.DataBean> dataBeanXDataList = dataBeanX.getData();
            if (dataBeanXDataList != null) {
                int index = 0;
                for (ZK31Bean.DataBeanX.DataBean dataBean : dataBeanXDataList) {
                    ArrayList list = new ArrayList();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(dataBean.getNumber(), dataBean.getName());
                        jsonObject.put(ZKFiled.ZK_FILED_DATA_BEAN, dataBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    moduleArray.put(jsonObject);

                    int type = Integer.parseInt(dataBean.getType());

//                    二级子数据，每一个 item。map 表示 右边需要的数据：
//     *                  ----1. 直接是数字，表示 右边的控件类型的 type.
//     *                  ----2. ArrayList 表示右边的控件类型需要默认数据：
//     *                  --------第0项表示 当前右边的类型 的 type;
//     *                  --------第1项表示 当前右边的类型 需要的默认数据，是一组数据列表。

                    list.add(type);
                    list.add(dataBean.getName());
                    try {
                        String str = dataBean.getDefault_list_data();
                        if (!TextUtils.isEmpty(str)) {
                            String[] strList = str.split(",");

                            String[] strings = new String[strList.length + 1];
                            for (int j = 0; j < strings.length; j++) {
                                if (j == 0) {
                                    strings[j] = dataBean.getDefaultX();
                                    continue;
                                }

                                strings[j] = strList[j - 1];
                            }

                            list.add(strings);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    objectHashMap.put(index, list);

                    index++;
                }
            }

            List titleList = new ArrayList();

            titleList.add(dataBeanX.getName());
            titleList.add(dataBeanX.getType());
//                    titleList.add(dataBean.getList()); // 这里如果有值的话，就是列表 String[]
            //                zkModuleLayout.setJsonArray(moduleArray, null, titleList);
            zkModuleLayout.setMoreData(isMoreData);
            zkModuleLayout.setJsonArray(moduleArray, objectHashMap, titleList);
            zkModuleLayouts.add(zkModuleLayout);
            addView(zkModuleLayout);
        }
    }

    @Override
    public JSONObject getResult() {
        JSONObject jsonObject = null;

        try {
            for (ZKModuleLayout zkModuleLayout :
                    zkModuleLayouts) {
                JSONObject zkModuleLayoutResult = zkModuleLayout.getResult();

                if (jsonObject == null) {
                    jsonObject = zkModuleLayoutResult;
                } else {
                    if (zkModuleLayoutResult != null) {
                        if (isMoreData) {
                            JSONArray jsonArray = null;
                            if (jsonObject.has("list")) {
                                jsonArray = jsonObject.optJSONArray("list");
                            }

                            if (jsonArray == null) {
                                jsonArray = new JSONArray();
                            }

                            jsonArray.put(zkModuleLayoutResult);
                            jsonObject.put("list", jsonArray);

                        } else {
                            JSONArray jsonArray = zkModuleLayoutResult.names();
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String key = jsonArray.optString(i);
                                    String value = zkModuleLayoutResult.optString(key);
                                    jsonObject.put(key, value);
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
