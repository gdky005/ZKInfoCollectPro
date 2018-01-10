package cc.zkteam.zkinfocollectpro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.RentPersonPresenterImpl;
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.RentPersonView;
import cc.zkteam.zkinfocollectpro.adapter.DateCollectRvAdapter;
import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.AddHouseParams;
import cc.zkteam.zkinfocollectpro.bean.RentPersoner;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.fragment.PersonalInfoCollectFragment;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.utils.CommonUtils;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;
import cc.zkteam.zkinfocollectpro.view.DividerItemDecoration;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 房屋 数据采集主页面
 * Created by Administrator on 2017/12/15.
 */

public class RentPersonInfoActivity extends BaseActivity implements RvListener, RentPersonView {

    RentPersonPresenterImpl mPresent;
    FragmentManager fragmentManager;

   /* @BindView(R.id.create_new)
    Button createNewRenter;

    @BindView(R.id.house_person_infos)
    LinearLayout personInfoContainer;*/

    @BindView(R.id.recycle_data)
    RecyclerView mRecycle;
    @BindView(R.id.tv_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pb_loading)
    ProgressBar mLoading;
    private RentPersoner rentperson;
    private DateCollectRvAdapter adapter;
    private String mBuildId;
    public static final String INVALID = "-1";
    private ZHApi mZhApi;
    private Dialog mEmigrationDialog;
    private String address;
    private AddHouseParams mAddHouseParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(mToolbar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_list_layout;
    }

    @Override
    protected void initViews() {

        fragmentManager = getSupportFragmentManager();
        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        mRecycle.addItemDecoration(new DividerItemDecoration(getResources().getColor(R.color.item_decor), CommonUtils.dip2px(this, 1)));
        mPresent = new RentPersonPresenterImpl(this);
        mToolbarTitle.setText("数据采集");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        rentperson = new RentPersoner();
        rentperson.setPersonlist(new ArrayList<>());
        mAddHouseParams = (AddHouseParams) intent.getSerializableExtra("params");
        Log.e("TaG", rentperson.getStatus() + "");
        mBuildId = intent.getStringExtra("build_Id");
        address = intent.getStringExtra("address");
        Log.e("TAG", address);
        if (rentperson != null) {
            List<RentPersoner.PersonlistBean> personlist = rentperson.getPersonlist();
            personlist.add(0, new RentPersoner.PersonlistBean(INVALID, INVALID, INVALID, INVALID));
            adapter = new DateCollectRvAdapter(this, personlist, this, address);
            mRecycle.setAdapter(adapter);
        }

        mZhApi = ZHConnectionManager.getInstance().getZHApi();

    }

    @Override
    public void onBackPressed() {
        Fragment current = FragmentUtils.getTopShow(fragmentManager);
        if (current != null) {
            FragmentUtils.remove(current);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onItemClick(int id, int position) {
        switch (id) {
            case R.id.caiji:
                PersonalInfoCollectFragment personalInfoCollectFragment = new PersonalInfoCollectFragment();
                Bundle bundle = new Bundle();
                bundle.putString(PersonalInfoCollectFragment.PERSON_ID, rentperson.getPersonlist().get(position).getP_id());
                personalInfoCollectFragment.setArguments(bundle);
                FragmentUtils.add(fragmentManager, personalInfoCollectFragment, R.id.container_layout);
                break;

            case R.id.out:
                showOutSetting(position);
                break;


            case R.id.push_serve:

                break;

            case R.id.create_new:
                Intent intent = new Intent();
                intent.putExtra("address", address);
                intent.putExtra("h_id", rentperson.getHouseid());
                intent.putExtra("b_id", mBuildId);

                PageCtrl.startActivity(this, NewResidentsInfoActivity.class, intent);
                return;
        }
//        Toast.makeText(mContext, "hello" + position, Toast.LENGTH_SHORT).show();
    }

    private void showOutSetting(int position) {
        View view = getLayoutInflater().inflate(R.layout.data_collect_out_setting, null, false);
        mEmigrationDialog = new Dialog(this);
        mEmigrationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mEmigrationDialog.setContentView(view);
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emigration(position);
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmigrationDialog.dismiss();
            }
        });

        mEmigrationDialog.show();
    }

    /**
     * 迁出
     *
     * @param position
     */
    private void emigration(int position) {
        mZhApi.emigration(rentperson.getPersonlist().get(position).getP_id(),
                rentperson.getPersonlist().get(position).getHouseid(), mBuildId).enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(@NonNull Call<ZHBaseBean> call, @NonNull Response<ZHBaseBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        ToastUtils.showShort("迁出成功");
                        rentperson.getPersonlist().remove(position);
                        adapter.cleanAndAddAll(rentperson.getPersonlist());
                    } else {
                        ToastUtils.showShort("迁出失败");
                    }
                }
                mEmigrationDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<ZHBaseBean> call, @NonNull Throwable t) {
                t.printStackTrace();
                mEmigrationDialog.dismiss();
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
    protected void onResume() {
        super.onResume();
        mLoading.setVisibility(View.VISIBLE);
        requestPersonList();
    }

    private void requestPersonList() {
        MultipartBody.Part community = MultipartBody.Part.createFormData("community", mAddHouseParams.getParams().get(0));
        MultipartBody.Part cunjuid = MultipartBody.Part.createFormData("cunjuid", mAddHouseParams.getParams().get(1));
        MultipartBody.Part gridding = MultipartBody.Part.createFormData("gridding", mAddHouseParams.getParams().get(2));
        MultipartBody.Part hsid = MultipartBody.Part.createFormData("buildid", mAddHouseParams.getParams().get(3));
        MultipartBody.Part houseSerial = MultipartBody.Part.createFormData("house_serial", mAddHouseParams.getParams().get(4));
        MultipartBody.Part address = MultipartBody.Part.createFormData("louceng", mAddHouseParams.getParams().get(5));
        MultipartBody.Part houseNumber = MultipartBody.Part.createFormData("house_number", mAddHouseParams.getParams().get(6));
        ZHConnectionManager.getInstance().getZHApi().addHouse(community, cunjuid, gridding, hsid,
                houseSerial, address, houseNumber).enqueue(new Callback<RentPersoner>() {
            @Override
            public void onResponse(Call<RentPersoner> call, Response<RentPersoner> response) {
                rentperson = response.body();
                if (rentperson != null) {
                    rentperson.getPersonlist().add(0, new RentPersoner.PersonlistBean(INVALID, INVALID, INVALID, INVALID));
                    adapter.cleanAndAddAll(rentperson.getPersonlist());
                }
                mLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RentPersoner> call, Throwable t) {
                mLoading.setVisibility(View.GONE);
            }
        });

    }
}
