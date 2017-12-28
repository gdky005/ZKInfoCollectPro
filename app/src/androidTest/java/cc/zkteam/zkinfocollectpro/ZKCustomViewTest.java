package cc.zkteam.zkinfocollectpro;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cc.zkteam.zkinfocollectpro.view.kind.ZKFiledLayoutView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKeyValueFiledView;

/**
 * ZKCustomViewTest 自定义View的单元测试
 * Created by wangqing on 2017/12/27.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class ZKCustomViewTest {
    Context context;

    @Before
    public void init() {
        context = InstrumentationRegistry.getTargetContext();
    }

    /**
     * 显示内容为：
     *  "姓名: 小黄"
     *
     *  布局使用方式：
     *  <cc.zkteam.zkinfocollectpro.view.kind.ZKFiledView
             android:id="@+id/filed_layout_view"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"/>
     */
    @Test
    public void testZKFieldView() {
        ZKKeyValueFiledView zkFiledView = new ZKKeyValueFiledView(context);
        zkFiledView.setKeyValue("姓名", "小黄");
        zkFiledView.setKey("年龄");
        zkFiledView.setValue("54");
    }

    /**
     * 显示内容为：
     *  "姓名: 小黄"        "年龄: 28"
     *  "性别: 男"          "位置: 北京"
     *  "开心: 非常开心"
     *
     *  布局使用方式：
     *      <cc.zkteam.zkinfocollectpro.view.kind.ZKFiledLayoutView
                android:id="@+id/filed_layout_view"
                 android:layout_width="match_parent"
                 android:layout_height="wrap_content"/>
     *
     */
    @Test
    public void testZKFiledLayoutView() {
        ZKFiledLayoutView layoutView = new ZKFiledLayoutView(context);

        try {
            JSONArray jsonArray = new JSONArray();

            jsonArray.put(new JSONObject().put("姓名", "王卿"));
            jsonArray.put(new JSONObject().put("年龄", "28"));
            jsonArray.put(new JSONObject().put("性别", "男"));
            jsonArray.put(new JSONObject().put("性别22", "男22"));

            layoutView.setJsonArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
