package cc.zkteam.zkinfocollectpro.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.BasicInfoItemBean;

/**
 * Created by popydog on 2017/12/28.
 */

public class BasinInfoItemHolder extends RvHolder<BasicInfoItemBean> {
    private RelativeLayout item;
    private ImageView itempic;
    private TextView itemtext;
    public BasinInfoItemHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);

        item=itemView.findViewById(R.id.item);
        itempic=itemView.findViewById(R.id.itempic);
        itemtext=itemView.findViewById(R.id.itemtext);
    }

    @Override
    public void bindHolder(BasicInfoItemBean basicInfoItemBean, int position) {
        itempic.setImageResource(basicInfoItemBean.image);
        itemtext.setText(basicInfoItemBean.itemname);
    }
}
