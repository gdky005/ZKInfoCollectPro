package cc.zkteam.zkinfocollectpro.fragment.datacollectfirst;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.ArgsInterface;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.MapActivity;
import cc.zkteam.zkinfocollectpro.activity.RentPersonInfoActivity;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.HouseInfo;
import cc.zkteam.zkinfocollectpro.bean.ZHCommunityBean;
import cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp.DcPresenterImpl;
import cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp.DcView;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;

//import cc.zkteam.zkinfocollectpro.activity.RentPersonInfoActivity;

/**
 * Created by Administrator on 2017/12/16.
 */

public class DataCollectFragment extends BaseFragment implements DcView, ArgsInterface {

    @Inject
    DcPresenterImpl mPresenter;

    @BindView(R.id.container_house)
    LinearLayout mHouseContainer;

    @BindView(R.id.cummunity_spinner)
    Spinner mCommunitySpinner;

    @BindView(R.id.road_spinner)
    Spinner mRoadSpinner;

    @BindView(R.id.house_num_spinner)
    Spinner mHouseSpinner;

    @BindView(R.id.neighborhood_spinner)
    Spinner mNeighborSpinner;

    @BindView(R.id.unit_spinner)
    Spinner mUnitSpinner;
    @BindView(R.id.tv_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ZHCommunityBean[] mCommunityBeans;

    public static DataCollectFragment newInstance() {

        Bundle args = new Bundle();

        DataCollectFragment fragment = new DataCollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data_collect_first;
    }

    @Override
    public void initView(View rootView) {
        mToolbarTitle.setText("数据采集");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mCommunityBeans = new ZHCommunityBean[5];
        mPresenter = new DcPresenterImpl(this);
        mPresenter.loadData();

    }

    @Override
    public void initListener() {
//        setSpinnerListener(mRoadSpinner ,"0");
//        setSpinnerListener(mCommunitySpinner ,"1");
//        setSpinnerListener(mNeighborSpinner ,"2");
//        setSpinnerListener(mHouseSpinner ,"3");
//        setSpinnerListener(mUnitSpinner ,null);
    }

    private void setSpinnerListener(Spinner spinner, String type) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (type != null) {
                    mPresenter.loadStreetCommunity(String.valueOf(id + 1), type);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onNoData() {

    }

    @Override
    public void onNetFinished() {

    }

    @Override
    public void requestFinish() {

    }


    @Override
    public void loadSpinner(ZHCommunityBean zhCommunity, String type) {
        Log.e("TAG", "loadSpinner-" + type);
        switch (type) {
            case "0":
                realLoadSpinner(mRoadSpinner, zhCommunity, type);
                break;
            case "1":
                realLoadSpinner(mCommunitySpinner, zhCommunity, type);
                break;
            case "2":
                realLoadSpinner(mNeighborSpinner, zhCommunity, type);
                break;
            case "3":
                realLoadSpinner(mHouseSpinner, zhCommunity, type);
                break;
            case "4":
                realLoadSpinner(mUnitSpinner, zhCommunity, type);
                break;
        }

    }

    private void realLoadSpinner(Spinner spinner, ZHCommunityBean zhCommunity, String type) {
        int index = Integer.valueOf(type);
        mCommunityBeans[index] = zhCommunity;
        if (!"4".contains(type)) {
            mPresenter.loadStreetCommunity("1", String.valueOf(index + 1));
        }
        fillSpinner(spinner, index);
    }


    private void fillSpinner(Spinner spinner, int index) {
        ZHCommunityBean communityBean = mCommunityBeans[index];
        if (communityBean.getData() == null) {
            return;
        }
        List<String> strings = getData(communityBean);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(mAdapter);
    }

    private List<String> getData(ZHCommunityBean communityBean) {
        List<String> strings = new ArrayList<>();
        for (ZHCommunityBean.DataBean dataBean : communityBean.getData()) {
            if (dataBean.getName() != null) {
                strings.add(dataBean.getName());
                Log.e("TAG", dataBean.getName());
            }
        }
        if (strings.isEmpty()) {
            strings.add("未获取到数据…");
        }
        return strings;
    }

    @Override
    public void updateRecycle(List<HouseInfo> mData) {
        for (int i = 0; i < mData.size(); i++) {

            LinearLayout linearLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext.getResources().getDimension(R.dimen.dp_40));
            params.leftMargin = (int) mContext.getResources().getDimension(R.dimen.dp_10);
            params.rightMargin = (int) mContext.getResources().getDimension(R.dimen.dp_10);
            linearLayout.setLayoutParams(params);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView floorNum = new TextView(mContext);
            int relFloor = i + 1;
            floorNum.setText(relFloor + "F");
            floorNum.setBackground(mContext.getResources().getDrawable(R.drawable.bg_table_shape_head));
            floorNum.setGravity(Gravity.CENTER);
            linearLayout.addView(floorNum, (int) mContext.getResources().getDimension(R.dimen.dp_40), LinearLayout.LayoutParams.MATCH_PARENT);
            Drawable mBg = mContext.getResources().getDrawable(R.drawable.bg_table_shape_item_even);
            if (i % 2 != 0) {
                mBg = mContext.getResources().getDrawable(R.drawable.bg_table_shape_item_odd);
            }

            int roomNum = mData.get(i).roomNum;
            for (int j = 1; j <= roomNum; j++) {
                TextView room = new TextView(mContext);
                room.setText(relFloor + "A0" + j);
                room.setGravity(Gravity.CENTER);
                room.setOnClickListener(new RoomClickListener(i + 1, j));
                room.setBackground(mBg);
                room.setTextColor(Color.parseColor("#666666"));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                linearLayout.addView(room, lp);
            }
            mHouseContainer.addView(linearLayout);
        }
    }

    class RoomClickListener implements View.OnClickListener {

        int mFloorNum;
        int mRoomNum;

        public RoomClickListener(int floorNum, int roomNum) {
            this.mFloorNum = floorNum;
            this.mRoomNum = roomNum;
        }

        @Override
        public void onClick(View view) {
            ToastUtils.showShort(mFloorNum + "--" + mRoomNum);
            PageCtrl.startActivity(getContext(), RentPersonInfoActivity.class);
        }
    }

}
