package cc.zkteam.zkinfocollectpro.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.adapter.DateCollectRvAdapter;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.RentInfo;
import cc.zkteam.zkinfocollectpro.utils.CommonUtils;
import cc.zkteam.zkinfocollectpro.view.DividerItemDecoration;

/**
 * Created by Administrator on 2017/12/15.
 */

public class DataListActivity extends BaseActivity implements RvListener {

    @BindView(R.id.recycle_data)
    RecyclerView mRecycle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.data_list_layout;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        List<RentInfo> infos = new ArrayList<>();
        RentInfo rentInfo1 = new RentInfo("序号", "姓名", "与户主关系", "更新", "操作");
        infos.add(rentInfo1);
        for (int i = 0; i < 5; i++) {
            RentInfo rentInfo = new RentInfo();
            rentInfo.name = "name" + i;
            rentInfo.num = i + "";
            rentInfo.relation = "relation" + i;
            rentInfo.update = "update" + i;
            rentInfo.operate = "operate" + i;
            infos.add(rentInfo);
        }

        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        mRecycle.addItemDecoration(new DividerItemDecoration(getResources().getColor(R.color.item_decor), CommonUtils.dip2px(this, 1)));
        mRecycle.setAdapter(new DateCollectRvAdapter(this, infos, this));
    }

    @Override
    public void onItemClick(int id, int position) {
        switch (id) {
            case R.id.caiji:

                break;

            case R.id.out:

                break;


            case R.id.push_serve:

                break;

            case R.id.update_person:

                break;
        }
        Toast.makeText(mContext, "hello"+position, Toast.LENGTH_SHORT).show();
    }
}
