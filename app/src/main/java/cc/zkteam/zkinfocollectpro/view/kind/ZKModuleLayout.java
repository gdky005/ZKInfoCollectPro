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
import java.util.List;
import java.util.Map;

import cc.zkteam.zkinfocollectpro.bean.ZK31Bean;

/**
 * ZKModuleLayout
 * 将 ZKKindTitle 和 ZKFormView 统一封装起来，更方便添加数据。
 * Created by WangQing on 2017/12/29.
 */

public class ZKModuleLayout extends ZKBaseView {

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

    /**
     * 设置数据
     *
     * @param jsonArray 二级子数据，每一个 item。
     *                  ----jsonObject 的 key 对应 ZKFiled 中的第一个参数 number，
     *                  ----value 表示后面紧跟的文字。
     * @param map       二级子数据，每一个 item。map 表示 右边需要的数据：
     *                  ----1. 直接是数字，表示 右边的控件类型的 type.
     *                  ----2. ArrayList 表示右边的控件类型需要默认数据：
     *                  --------第0项表示 当前右边的类型 的 type;
     *                  --------第1项表示 当前右边的类型 需要的默认数据，是一组数据列表。
     * @param tileList  一级大标题后面 出现的   数据的单选list （PS: 一个 module 只能有一个这个）
     *                  --------第0项表示 当前标题 的 名字;
     *                  --------第1项表示 当前标题 的 type；
     *                  --------第2项表示 当前标题 需要的默认数据，是一组 String[] 数据列表。
     */
    public void setJsonArray(JSONArray jsonArray, Map<Integer, Object> map, List tileList) {
        setOrientation(VERTICAL);

        try {
            ZKKindTitle zkKindTitle = new ZKKindTitle(context);
            String name = String.valueOf(tileList.get(0));
            int type = Integer.valueOf(String.valueOf(tileList.get(1)));
            String[] strings = new String[0];
            try {
                strings = (String[]) tileList.get(2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            zkKindTitle.setData(name, type, strings);
            addView(zkKindTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            // 2018/1/2  提前遍历这个的数据，找出字数最多的，设置为所有的 text 的 ems属性
            int longestTextSize = 0;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                if (object != null) {
                    String key = object.names().optString(0);
                    String value = object.optString(key);

                    if (!TextUtils.isEmpty(value)) {
                        if (longestTextSize < value.length()) {
                            longestTextSize = value.length();
                        }
                    }
                }
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                if (object != null) {
                    String key = object.names().optString(0);
                    Object value = object.optString(key);

                    ZK31Bean.DataBeanX.DataBean dataBean = (ZK31Bean.DataBeanX.DataBean) object.get(ZKFiled.ZK_FILED_DATA_BEAN);

                    ZKFiled zkFiled = new ZKFiled(getContext());
                    zkFiled.setKeyFiledTextLength(longestTextSize);
                    zkFiled.setZkFiledDataBean(dataBean);

                    int type = ZKFiled.TYPE_FILED_FORM_EDIT_TEXT;

                    Object defaultValue = null;

                    if (map != null && map.size() > 0) {
                        Object obj = map.get(i);

                        if (obj instanceof ArrayList) {
                            ArrayList list = (ArrayList) obj;
                            type = (int) list.get(0);
                            try {
                                value = list.get(1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                defaultValue = list.get(2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (obj instanceof Integer) {
                            type = (Integer) obj;
                        }
                    }

                    if (null == defaultValue) {
                        defaultValue = dataBean.getDefaultX();
                    }

                    zkFiled.setData(key, (String) value, defaultValue, i, type);

                    zkFiledList.add(zkFiled);
                    addView(zkFiled);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getResult() {
        JSONObject jsonObject = new JSONObject();


        try {

            for (int i = 0; i < zkFiledList.size(); i++) {
                ZKFiled zkFiled = zkFiledList.get(i);
                Map map = zkFiled.getResult();
                if (map != null) {
                    String tableName = (String) map.get(ZKFiled.ZK_FILED_TABLE_NAME);
                    String newValue = (String) map.get(ZKFiled.ZK_FILED_NEW_VALUE);

                    if (!TextUtils.isEmpty(tableName) && tableName.contains("[]")) {
                        String newKey = String.valueOf(i);

                        String newTableName = tableName.replaceAll("\\[]", "");

                        if (jsonObject.has(newTableName)) {
                            JSONObject obj = jsonObject.optJSONObject(newTableName);
                            obj.put(newKey, newValue);

                            jsonObject.put(newTableName, obj);
                        } else {
                            JSONObject obj = new JSONObject();
                            obj.put(newKey, newValue);

                            jsonObject.put(newTableName, obj);
                        }
                    } else {
                        jsonObject.put(tableName, newValue);
                    }
                }
            }

            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
