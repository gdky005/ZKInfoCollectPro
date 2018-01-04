package cc.zkteam.zkinfocollectpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.RvAdapter;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.CollectItemBean;
import cc.zkteam.zkinfocollectpro.view.ZKImageView;

/**
 * Created by loong on 2017/12/15.
 */

public class PersonalInfoListAdapter extends RvAdapter<CollectItemBean.DataBean> {

    public PersonalInfoListAdapter(Context context, List<CollectItemBean.DataBean> data, RvListener listener) {
        super(context, data, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_personal_info;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ItemHolder(view, viewType, null);
    }

    class ItemHolder extends RvHolder<CollectItemBean.DataBean> {
        ZKImageView imgPersonalInfoIcon;
        ImageView imgPersonalInfoItemEdit;
        TextView tvPersonalInfoItemTitle;

        public ItemHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            imgPersonalInfoIcon = itemView.findViewById(R.id.img_personal_info_icon);
            imgPersonalInfoItemEdit = itemView.findViewById(R.id.img_personal_info_item_edit);
            tvPersonalInfoItemTitle = itemView.findViewById(R.id.tv_personal_info_item_title);
        }

        @Override
        public void bindHolder(CollectItemBean.DataBean dataBean, int position) {
            if (dataBean == null) return;
            if ("default.gif".equals(dataBean.getIco())) {
                int id = mContext.getResources().getIdentifier("basininfoitem" + position, "drawable", mContext.getPackageName());
                imgPersonalInfoIcon.setImageResource(id);
            } else {
                imgPersonalInfoIcon.setImageURI(dataBean.getIco());
            }
            setText(tvPersonalInfoItemTitle, dataBean.getName() + "   " + dataBean.getNum() + "项");
            imgPersonalInfoItemEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(v.getId(), position);
                }
            });
        }
    }
}
