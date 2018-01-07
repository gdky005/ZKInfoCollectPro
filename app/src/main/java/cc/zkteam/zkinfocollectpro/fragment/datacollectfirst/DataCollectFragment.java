package cc.zkteam.zkinfocollectpro.fragment.datacollectfirst;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.ArgsInterface;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.RentPersonInfoActivity;
import cc.zkteam.zkinfocollectpro.adapter.HouseUnitAdapter;
import cc.zkteam.zkinfocollectpro.adapter.LDSpinnerAdapter;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.AddHouseParams;
import cc.zkteam.zkinfocollectpro.bean.HouseInfo;
import cc.zkteam.zkinfocollectpro.bean.RentPersoner;
import cc.zkteam.zkinfocollectpro.bean.ZHCommunityBean;
import cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp.DcPresenterImpl;
import cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp.DcView;
import cc.zkteam.zkinfocollectpro.utils.MapBean;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;
import okhttp3.MultipartBody;

//import cc.zkteam.zkinfocollectpro.activity.RentPersonInfoActivity;

/**
 * Created by Administrator on 2017/12/16.
 */

public class DataCollectFragment extends BaseFragment implements DcView, ArgsInterface, AdapterView.OnItemSelectedListener {

    public static final String TYPE_FANG_WU_XIN_XI_TYPE = "fangwuxinxi_type";

    private String floorNum;
    private String roomNum;

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
    @BindView(R.id.pb_loading)
    ProgressBar mLoading;
    private LDSpinnerAdapter roadAdapter;
    private LDSpinnerAdapter communityAdapter;
    private LDSpinnerAdapter houseAdapter;
    private LDSpinnerAdapter neibAdapter;
    private LDSpinnerAdapter unitAdapter;
    private HashMap<Integer, LDSpinnerAdapter> adapterMap;
    private HashMap<Integer, Spinner> spinnerMap;
    private HashMap<Integer, String> tempIds;
    private StringBuffer mAddress = new StringBuffer();


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
        showLoading();
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
        roadAdapter = new LDSpinnerAdapter(mContext, 0);
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

        if ("0".equals(type)) {
            mPresenter.loadStreetCommunity(zhCommunity.getData().get(0).getId(), "1");
            tempIds.put(0, zhCommunity.getData().get(0).getId());
            showLoading();
        }
    }

    @Override
    public void updata(RentPersoner data) {
        List<RentPersoner.PersonlistBean> personlist = data.getPersonlist();
        if (data.getStatus() == 2) {
            Intent intent = new Intent();
            AddHouseParams params = new AddHouseParams(tempIds);
            intent.putExtra("params", params);
            intent.putExtra("build_Id", tempIds.get(3));
            intent.putExtra("address", mAddress.toString());
            Log.e("TAG", mAddress.toString());
            PageCtrl.startActivity(getContext(), RentPersonInfoActivity.class, intent);
            mAddress.delete(0, mAddress.length());
            Log.e("TAG", mAddress.toString());
        } else if (data.getStatus() == 1) {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.create_house_dialog, null, false);
                Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                view.findViewById(R.id.confirm).setOnClickListener(v -> {
                    MapBean mapBean = new MapBean();

                    Map<String, String>  map = new HashMap<>();
                    map.put("type", TYPE_FANG_WU_XIN_XI_TYPE);

                    map.put("community", getAddressName(mRoadSpinner));
                    map.put("cunjuid", getAddressName(mCommunitySpinner));
                    map.put("gridding", getAddressName(mNeighborSpinner));
                    map.put("buildid", getAddressName(mHouseSpinner));
                    map.put("house_serial", mUnitSpinner.getSelectedItem().toString());
                    map.put("louceng", floorNum);
                    map.put("house_number", roomNum);

                    mapBean.setMap(map);

                    PageCtrl.startNew31InfoActivity(getActivity(), "房屋信息", TYPE_FANG_WU_XIN_XI_TYPE, mapBean);
                });
                view.findViewById(R.id.cancel).setOnClickListener(view12 -> dialog.dismiss());

                dialog.show();
            }
        }
    }

    @Override
    public void showLoading() {
        if (mLoading == null) {
            return;
        }
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (mLoading == null) {
            return;
        }
        mLoading.setVisibility(View.GONE);
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
                room.setText(relFloor + "0" + j);
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
            tempIds.put(index, id);
            Log.i("chris", "onItemSelected: " + "type: " + index + "--id--:" + id);
            index++;
            showLoading();
            mPresenter.loadStreetCommunity(id, index + "");

        } else {
            // TODO: 2017/12/25 调用更新住房信息接口
            LDSpinnerAdapter adapter1 = adapterMap.get(index);
            tempIds.put(index, i + "");
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
            showLoading();
            getAddress(mFloorNum, mRoomNum);

            floorNum = mFloorNum  + "";
            roomNum = "0" + mRoomNum;

            tempIds.put(5, floorNum);
            tempIds.put(6, roomNum);

            getRentPersoner(tempIds);
        }
    }

    private void getAddress(int floorNum, int roomNum) {
        mAddress.append(getAddressName(mRoadSpinner));
        mAddress.append(" ");
        mAddress.append(getAddressName(mCommunitySpinner));
        mAddress.append(" ");
        mAddress.append(getAddressName(mNeighborSpinner));
        mAddress.append(" ");
        mAddress.append(getAddressName(mHouseSpinner));
        mAddress.append(" ");
        mAddress.append(mUnitSpinner.getSelectedItem().toString());
        mAddress.append(" ");
        mAddress.append(floorNum);
        if (String.valueOf(roomNum).length() <= 1) mAddress.append("0");
        mAddress.append(roomNum);
        mAddress.append("室");
    }

    private String getAddressName(Spinner spinner) {
        ZHCommunityBean.DataBean dataBean = (ZHCommunityBean.DataBean) spinner.getSelectedItem();
        return dataBean.getName();
    }

    private void getRentPersoner(HashMap<Integer, String> params) {
        MultipartBody.Part community = MultipartBody.Part.createFormData("community", params.get(0));
        MultipartBody.Part cunjuid = MultipartBody.Part.createFormData("cunjuid", params.get(1));
        MultipartBody.Part gridding = MultipartBody.Part.createFormData("gridding", params.get(2));
        MultipartBody.Part hsid = MultipartBody.Part.createFormData("buildid", params.get(3));
        MultipartBody.Part houseSerial = MultipartBody.Part.createFormData("house_serial", params.get(4));
        MultipartBody.Part address = MultipartBody.Part.createFormData("louceng", params.get(5));
        MultipartBody.Part houseNumber = MultipartBody.Part.createFormData("house_number", params.get(6));
        mPresenter.loadRentInfo(community, cunjuid, gridding,
                hsid, houseSerial, address, houseNumber);
    }
}
