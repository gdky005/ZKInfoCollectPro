package cc.zkteam.zkinfocollectpro.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.test.ZK31Bean;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.managers.ZHMemoryCacheManager;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.utils.baidu.Base64Util;
import cc.zkteam.zkinfocollectpro.utils.baidu.FileUtil;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiled;
import cc.zkteam.zkinfocollectpro.view.kind.ZKModuleListLayout;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * NewBasicInfoFragment
 * Created by WangQing on 2018/1/2.
 */

public class New31InfoFragment extends BaseFragment {
    private static final String TAG = "New31InfoFragment";

    public static final String NEW_31_INFO_NAME_KEY = "name";
    public static final String NEW_31_INFO_PAGE_TYPE_KEY = "pageType";
    public static final int REQUEST_CODE = 300;

    @BindView(R.id.new_31_zk_module_list_layout)
    ZKModuleListLayout new31ZkModuleListLayout;
    @BindView(R.id.new_31_commit)
    Button new31Commit;
    @BindView(R.id.new_31_ll)
    LinearLayout new31Ll;
    @BindView(R.id.zk_31_new_loading_rl)
    RelativeLayout zk31NewLoadingRl;

    private String titleName;
    private String pageType;

    private String userID;
    private New31ImageEvent imageEvent;

    public static New31InfoFragment newInstance(String name, String pageType) {
        Bundle args = new Bundle();
        New31InfoFragment fragment = new New31InfoFragment();
        args.putString(NEW_31_INFO_NAME_KEY, name);
        args.putString(NEW_31_INFO_PAGE_TYPE_KEY, pageType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_31_basicinfo_fragment;
    }


    @Subscribe
    public void imageClickListener(New31ImageEvent imageEvent) {
        this.imageEvent = imageEvent;

        MultiImageSelector.create()
                .showCamera(false)
                .single() // single mode
                .start(this, REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 2018/1/5  处理事件

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // 获取返回的图片
            List<String> pics = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

            if (pics != null && pics.size() > 0) {
                String galleyPicPath = pics.get(0);
                L.d("当前图片地址是：" + galleyPicPath);

                if (imageEvent != null) {
                    ZKFiled zkFiled = imageEvent.zkFiled;

                    if (zkFiled != null) {
                        int type = imageEvent.type;

                        if (ZKFiled.TYPE_FILED_FORM_IMAGE == type) {
                            zkFiled.defaultValue = galleyPicPath;

                            ImageView rightLayoutImageView = zkFiled.findViewById(R.id.right_layout_image_view);
                            rightLayoutImageView.setImageURI(Uri.parse(galleyPicPath));
                        } else if (ZKFiled.TYPE_FILED_FORM_ID_CARD == type) {
                            ImageView rightLayoutIdCardLeft = zkFiled.findViewById(R.id.right_layout_id_card_left);
                            ImageView rightLayoutIdCardRight = zkFiled.findViewById(R.id.right_layout_id_card_right);

                            String[] strings = (String[]) zkFiled.defaultValue;
                            if (strings == null) {
                                strings = new String[2];
                            }

                            if (imageEvent.isIdcardLeft) {
                                strings[0] = galleyPicPath;
                                rightLayoutIdCardLeft.setImageURI(Uri.parse(galleyPicPath));
                            } else {
                                strings[1] = galleyPicPath;
                                rightLayoutIdCardRight.setImageURI(Uri.parse(galleyPicPath));
                            }

                            zkFiled.defaultValue = strings;
                        }
                    }
                }
            }
        } else {
            ToastUtils.showShort("取消操作");
        }

        this.imageEvent = null;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void initView(View rootView) {
        setVisibility(new31Ll, false);
        setVisibility(new31Commit, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            titleName = bundle.getString(NEW_31_INFO_NAME_KEY);
            pageType = bundle.getString(NEW_31_INFO_PAGE_TYPE_KEY);
        }

        // TODO: 2018/1/4 test
        if (TextUtils.isEmpty(pageType)) {
            pageType = "renyuanxinxi_type";
        }

        // TODO: 2018/1/4 test
        if (TextUtils.isEmpty(titleName)) {
            titleName = "人员信息";
        }

        TitleEvent titleEvent = new TitleEvent();
        titleEvent.title = titleName;
        EventBus.getDefault().post(titleEvent);

        userID = ZHMemoryCacheManager.getInstance().getUserId();

        // TODO: 2018/1/2 test 数据
        if (TextUtils.isEmpty(userID)) {
            userID = "2";
        }

        showZKModuleAPI(new31ZkModuleListLayout, pageType);
    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.new_31_commit)
    public void onViewClicked() {
        setVisibility(zk31NewLoadingRl, true);
        JSONObject resultObj = new31ZkModuleListLayout.getResult();

        setImageData(resultObj);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", userID);
            jsonObject.put("data", resultObj);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            Call<ZHBaseBean> zhBaseBeanCall = ZHConnectionManager.getInstance().getZHApi().update31Data(body);
            zhBaseBeanCall.enqueue(new Callback<ZHBaseBean>() {
                @Override
                public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                    Log.d(TAG, "onResponse: " + response);
                    if (response.body() != null && response.body().getStatus() == 1) {
                        ToastUtils.showShort("数据提交成功");
                        setVisibility(zk31NewLoadingRl, false);
                        return;
                    }

                    ToastUtils.showShort("数据提交失败");
                    setVisibility(zk31NewLoadingRl, false);
                }

                @Override
                public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                    ToastUtils.showShort("数据提交失败：" + t.getMessage());
                    setVisibility(zk31NewLoadingRl, false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setImageData(JSONObject resultObj) {

        if (resultObj == null) {
            return;
        }

        JSONArray jsonArray = resultObj.names();

        for (int i = 0; i < jsonArray.length(); i++) {
            String key = jsonArray.optString(i);
            String value = resultObj.optString(key);

            if (!TextUtils.isEmpty(value)) {
                if (value.contains("/")) {

                    if (value.contains(",")) {
                        String[] filePaths = value.split(",");

                        String twoBase64EncodeImage = "";
                        for (String path :
                                filePaths) {
                            if (FileUtils.isFile(path)) {
                                try {
                                    byte[] imgData = FileUtil.readFileByBytes(path);
                                    String imgStr = Base64Util.encode(imgData);
                                    String base64EncodeImage = URLEncoder.encode(imgStr, "UTF-8");


                                    if (TextUtils.isEmpty(twoBase64EncodeImage)) {
                                        twoBase64EncodeImage = base64EncodeImage;
                                    } else {
                                        twoBase64EncodeImage = "," + base64EncodeImage;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        try {
                            if (!TextUtils.isEmpty(twoBase64EncodeImage))
                                resultObj.put(key, twoBase64EncodeImage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        if (FileUtils.isFile(value)) {
                            try {
                                byte[] imgData = FileUtil.readFileByBytes(value);
                                String imgStr = Base64Util.encode(imgData);
                                String base64EncodeImage = URLEncoder.encode(imgStr, "UTF-8");
                                resultObj.put(key, base64EncodeImage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private void showZKModuleAPI(ZKModuleListLayout zkModuleListLayout, String pageType) {
        ZHConnectionManager.getInstance().getZHApi().get31Data(pageType, userID).enqueue(new Callback<ZK31Bean>() {

            @Override
            public void onResponse(Call<ZK31Bean> call, Response<ZK31Bean> result) {
                ZK31Bean zk31Bean = result.body();

                if (zk31Bean != null) {
                    List<ZK31Bean.DataBeanX> dataBeanList = zk31Bean.getData();
                    if (zkModuleListLayout != null) {
                        setVisibility(new31Commit, true);
                        zkModuleListLayout.setDataBeanList(dataBeanList);
                        requestFinish();
                        return;
                    }
                }

                ToastUtils.showShort("请求数据异常");
            }

            @Override
            public void onFailure(Call<ZK31Bean> call, Throwable throwable) {
                L.e("onFailure: ", throwable.getMessage());
                requestFinish();
                ToastUtils.showShort(throwable.getMessage());
            }
        });
    }

    private void requestFinish() {
        setVisibility(new31Ll, true);
        setVisibility(zk31NewLoadingRl, false);
    }
}
