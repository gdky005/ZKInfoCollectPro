package cc.zkteam.zkinfocollectpro.fragment.datacollectfirst;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.ArgsInterface;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.RentPersonInfoActivity;
import cc.zkteam.zkinfocollectpro.adapter.HouseUnitAdapter;
import cc.zkteam.zkinfocollectpro.adapter.LDSpinnerAdapter;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.HouseInfo;
import cc.zkteam.zkinfocollectpro.bean.RentInfoParam;
import cc.zkteam.zkinfocollectpro.bean.ZHCommunityBean;
import cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp.DcPresenterImpl;
import cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp.DcView;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;

//import cc.zkteam.zkinfocollectpro.activity.RentPersonInfoActivity;

/**
 * Created by Administrator on 2017/12/16.
 */

public class DataCollectFragment extends BaseFragment implements DcView, ArgsInterface, AdapterView.OnItemSelectedListener {

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
    private LDSpinnerAdapter roadAdapter;
    private LDSpinnerAdapter communityAdapter;
    private LDSpinnerAdapter houseAdapter;
    private LDSpinnerAdapter neibAdapter;
    private LDSpinnerAdapter unitAdapter;
    private HashMap<Integer, LDSpinnerAdapter> adapterMap;
    private HashMap<Integer, Spinner> spinnerMap;
    private HashMap<Integer,String> tempIds;


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
        initSpinner();
        initCache();
        tempIds = new HashMap<>();
        mPresenter = new DcPresenterImpl(this);
        mPresenter.loadData();
    }

    private void initCache() {
        adapterMap = new HashMap<>();
        adapterMap.put(0, roadAdapter);
        adapterMap.put(1, communityAdapter);
        adapterMap.put(2, neibAdapter);
        adapterMap.put(3, houseAdapter);
        adapterMap.put(4, unitAdapter);

        spinnerMap = new HashMap<>();
        spinnerMap.put(0, mRoadSpinner);
        spinnerMap.put(1, mCommunitySpinner);
        spinnerMap.put(2, mNeighborSpinner);
        spinnerMap.put(3, mHouseSpinner);
        spinnerMap.put(4, mUnitSpinner);

    }

    private void initSpinner() {
        roadAdapter = new LDSpinnerAdapter(mContext);
        mRoadSpinner.setAdapter(roadAdapter);
        mRoadSpinner.setOnItemSelectedListener(this);

        communityAdapter = new LDSpinnerAdapter(mContext);
        mCommunitySpinner.setAdapter(communityAdapter);
        mCommunitySpinner.setOnItemSelectedListener(this);

        houseAdapter = new LDSpinnerAdapter(mContext);
        mHouseSpinner.setAdapter(houseAdapter);
        mHouseSpinner.setOnItemSelectedListener(this);

        neibAdapter = new LDSpinnerAdapter(mContext);
        mNeighborSpinner.setAdapter(neibAdapter);
        mNeighborSpinner.setOnItemSelectedListener(this);

        unitAdapter = new HouseUnitAdapter(mContext);
        mUnitSpinner.setAdapter(unitAdapter);
        mUnitSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void initListener() {

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

        if (zhCommunity.getData() == null) {
            return;
        }
        Log.e("TAG", "loadSpinner-" + type);
        int index = Integer.valueOf(type);
        LDSpinnerAdapter adapter = adapterMap.get(index);
        if (adapter != null) {
            adapter.setData(zhCommunity.getData());
        }
    }


    @Override
    public void updateRecycle(List<HouseInfo> mData) {
        mHouseContainer.removeViews(1, mHouseContainer.getChildCount() - 1);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int index = -1;

        switch (adapterView.getId()) {
            case R.id.road_spinner:
                index = 0;
                break;

            case R.id.cummunity_spinner:
                index = 1;
                break;

            case R.id.neighborhood_spinner:
                index = 2;
                break;

            case R.id.house_num_spinner:
                index = 3;
                break;
            case R.id.unit_spinner:
                index = 4;
                break;
        }

        if (i == 0) {   //选择第一个数据不做任何动作
            return;
        }

        LDSpinnerAdapter adapter = adapterMap.get(index);
        if (index < 4) {
            clearSpinnerData(index);
            String id = ((ZHCommunityBean.DataBean) adapter.getItem(i)).getId();
            tempIds.put(index,id);
            Log.i("chris", "onItemSelected: "+"type: "+ index +"--id--:"+id);
            index++;
            mPresenter.loadStreetCommunity(id, index + "");

        } else if (index == 4) {
            // TODO: 2017/12/25 调用更新住房信息接口
            LDSpinnerAdapter adapter1 = adapterMap.get(index);
            tempIds.put(index,i+"");
            List<HouseInfo> houseInfos = new ArrayList<>();
            for (int j = 1; j <= adapter1.getmCeng(); j++) {
                HouseInfo houseInfo = new HouseInfo(j, adapter1.getHome());
                houseInfos.add(houseInfo);
            }
            updateRecycle(houseInfos);
        }
    }


    private void clearSpinnerData(int startIndex) {
        for (int i = startIndex + 1; i <= 4; i++) {
            adapterMap.get(i).reSetData();
        }
        mHouseContainer.removeViews(1, mHouseContainer.getChildCount() - 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.i("chris", "onNothingSelected: ");
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
            tempIds.put(5,mFloorNum+"");
            tempIds.put(6,"0"+mRoomNum+"");

            Intent intent = new Intent();
            RentInfoParam param = new RentInfoParam(tempIds);
            intent.putExtra("rent_params",param);
            ToastUtils.showShort(mFloorNum + "--" + mRoomNum);
            PageCtrl.startActivity(getContext(), RentPersonInfoActivity.class,intent);
        }
    }

}
