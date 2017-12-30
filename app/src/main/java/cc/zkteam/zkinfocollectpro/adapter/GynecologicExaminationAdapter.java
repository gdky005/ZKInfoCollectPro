package cc.zkteam.zkinfocollectpro.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.RvAdapter;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.GynecologicExaminationBean;
import cc.zkteam.zkinfocollectpro.viewholder.ChildInfoHolder;
import cc.zkteam.zkinfocollectpro.viewholder.GynecologicExaminationHolder;

/**
 * Created by Administrator on 2017/12/30.
 */

public class GynecologicExaminationAdapter extends RvAdapter<GynecologicExaminationBean> {
    private List<GynecologicExaminationBean> mGynecologicExaminationBeans;
    private View.OnClickListener mDeleteListener;

    public GynecologicExaminationAdapter(Context context, List<GynecologicExaminationBean> data, RvListener listener) {
        super(context, data, listener);
        mGynecologicExaminationBeans = data;
    }

    public GynecologicExaminationAdapter(Context context, List<GynecologicExaminationBean> data, RvListener listener,
                                         View.OnClickListener deleteListener) {
        this(context, data, listener);
        mDeleteListener = deleteListener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_gynecologic_examination;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new GynecologicExaminationHolder(view, viewType, null, mDeleteListener);
    }
}
