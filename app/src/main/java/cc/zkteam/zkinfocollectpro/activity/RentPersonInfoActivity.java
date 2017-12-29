package cc.zkteam.zkinfocollectpro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.RentPersonPresenterImpl;
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.RentPersonView;
import cc.zkteam.zkinfocollectpro.adapter.DateCollectRvAdapter;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.RentInfo;
import cc.zkteam.zkinfocollectpro.bean.RentInfoParam;
import cc.zkteam.zkinfocollectpro.fragment.PersonalInfoCollectFragment;
import cc.zkteam.zkinfocollectpro.utils.CommonUtils;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;
import cc.zkteam.zkinfocollectpro.view.DividerItemDecoration;
import okhttp3.MultipartBody;

/**
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
    private RentInfoParam rent_params;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        rent_params = (RentInfoParam) intent.getSerializableExtra("rent_params");
        Map<Integer, String> params = rent_params.getParams();
        MultipartBody.Part community = MultipartBody.Part.createFormData("community", params.get(0));
        MultipartBody.Part cunjuid = MultipartBody.Part.createFormData("cunjuid", params.get(1));
        MultipartBody.Part gridding = MultipartBody.Part.createFormData("gridding", params.get(2));
        MultipartBody.Part hsid = MultipartBody.Part.createFormData("buildid", params.get(3));
        MultipartBody.Part houseSerial = MultipartBody.Part.createFormData("house_serial", params.get(4));
        MultipartBody.Part address = MultipartBody.Part.createFormData("louceng", params.get(5));
        MultipartBody.Part houseNumber = MultipartBody.Part.createFormData("house_number", params.get(6));
        mPresent.loadRentInfo(community, cunjuid, gridding,
                hsid, houseSerial, address, houseNumber);
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
//        mPresent.loadData();

        mToolbarTitle.setText("数据采集");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

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
                ToastUtils.showShort("采集信息");
                FragmentUtils.add(fragmentManager, new PersonalInfoCollectFragment(), R.id.container_layout);
                break;

            case R.id.out:
                showOutSetting(position);
                break;


            case R.id.push_serve:

                break;

            case R.id.create_new:

                PageCtrl.startActivity(this, NewResidentsInfoActivity.class);
                return;
        }
        Toast.makeText(mContext, "hello" + position, Toast.LENGTH_SHORT).show();
    }

    private void showOutSetting(int position) {
        View view = getLayoutInflater().inflate(R.layout.data_collect_out_setting, null, false);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(view);
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

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
    public void updata(List<RentInfo> data) {
        DateCollectRvAdapter adapter = new DateCollectRvAdapter(this, data, this);
        mRecycle.setAdapter(adapter);

        // adapter.notifyItemChanged();
    }

}
