package cc.zkteam.zkinfocollectpro.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.RentPersonPresenterImpl;
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.RentPersonView;
import cc.zkteam.zkinfocollectpro.adapter.DateCollectRvAdapter;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.RentInfo;
import cc.zkteam.zkinfocollectpro.utils.CommonUtils;
import cc.zkteam.zkinfocollectpro.view.DividerItemDecoration;

/**
 * Created by Administrator on 2017/12/15.
 */

public class RentPersonInfoActivity extends BaseActivity implements RvListener, RentPersonView {

    RentPersonPresenterImpl mPresent;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_list_layout;
    }

    @Override
    protected void initViews() {
        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        mRecycle.addItemDecoration(new DividerItemDecoration(getResources().getColor(R.color.item_decor), CommonUtils.dip2px(this, 1)));
        mPresent = new RentPersonPresenterImpl(this);
        mPresent.loadData();

        initToolbar(mToolbar);
        mToolbarTitle.setText("数据采集");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
    }

    @Override
    public void onItemClick(int id, int position) {
        switch (id) {
            case R.id.caiji:

                break;

            case R.id.out:
                showOutSetting(position);
                break;


            case R.id.push_serve:

                break;

            case R.id.create_new:

                break;
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
