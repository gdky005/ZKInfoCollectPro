package cc.zkteam.zkinfocollectpro.adapter;

import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.bean.ZK31Bean;

/**
 * Created by WangQing on 2018/1/1.
 */

public class TestAdapter extends BaseQuickAdapter<ZK31Bean, BaseViewHolder> {


    public TestAdapter(@Nullable List<ZK31Bean> data) {
        super(R.layout.test_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZK31Bean item) {
       LinearLayout linearLayout = helper.getView(R.id.item_root_view);
//        root_view







//       linearLayout.addView();

    }
}
