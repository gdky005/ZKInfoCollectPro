package cc.zkteam.zkinfocollectpro.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.RvAdapter;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.RentPersoner;

/**
 * Created by Administrator on 2017/12/15.
 */

public class DataCollectRvHolder extends RvHolder<RentPersoner.PersonlistBean> {
    private RvAdapter adapter;
    private Context mContext;
    private String mAddress = "";

    public DataCollectRvHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);
        mContext = itemView.getContext();
    }

    @Override
    public void bindHolder(RentPersoner.PersonlistBean personlistBean, int position) {

    }


    private TextView findTv(int id) {
        return ((TextView) itemView.findViewById(id));
    }

    private Button findBtn(int id) {
        return ((Button) itemView.findViewById(id));
    }

    public void setAdapter(RvAdapter dateCollectRvAdapter) {
        this.adapter = dateCollectRvAdapter;
    }



    @Override
    public void bindHolder(List<RentPersoner.PersonlistBean> data, final int position) {
        if (mType == 0) {
            TextView address = findTv(R.id.tv_address);
            if (address != null) {
                address.setText("");
                address.setText(mAddress);
            }
            TextView btn = findTv(R.id.create_new);
            if (btn != null) {
                btn.setOnClickListener(view -> mListener.onItemClick(view.getId(), position));
            }
        } else if (mType == 1) {
            final int rePosiont = position - 1;
            RentPersoner.PersonlistBean rentInfo = data.get(rePosiont);


            TextView num = findTv(R.id.num);
            TextView name = findTv(R.id.name);
            TextView relation = findTv(R.id.relation);
            ImageView update = ((ImageView) itemView.findViewById(R.id.update));
            TextView operate_title = findTv(R.id.operate_title);
            TextView caiji = findTv(R.id.caiji);
            TextView out = findTv(R.id.out);
            View imageContainer = itemView.findViewById(R.id.image_container);
            TextView updateTitle = findTv(R.id.update_title);

            caiji.setOnClickListener(view -> mListener.onItemClick(view.getId(), rePosiont));

            out.setOnClickListener(view -> mListener.onItemClick(view.getId(), rePosiont));

            View operate = itemView.findViewById(R.id.operate);

            if (rePosiont == 0 && "-1".equals(rentInfo.getName())) {   //标题头
                  showTitle(operate, operate_title);

                initTextColor(Color.parseColor("#ffffff"), num, name, updateTitle, relation, operate_title);
                initViewColor(Color.parseColor("#3ba3d0"), num, name, relation, imageContainer, updateTitle, operate, operate_title);
                // return;
            } else if (rePosiont % 2 == 0) {      //偶数行
                initTextColor(Color.parseColor("#333333"), num, name, relation, caiji, out);
                showContent(operate, operate_title);
//                showContent(imageContainer, updateTitle);
                initViewColor(Color.parseColor("#ebf6fa"), num, name, relation, imageContainer, operate);
            } else {     //奇数行
                initTextColor(Color.parseColor("#333333"), num, name, relation, caiji, out);
                showContent(operate, operate_title);
//                showContent(imageContainer, updateTitle);
                initViewColor(Color.parseColor("#dff0f7"), num, name, relation, imageContainer, operate);
            }

//            updateTitle.setText(rentInfo.update);
//            operate_title.setText(rentInfo.operate);
            if ("-1".equals(rentInfo.getName())) {
                num.setText("序号");
                operate_title.setText("操作");
                relation.setText("关系");
                name.setText("姓名");
                updateTitle.setText("更新");
            } else {
                num.setText(rePosiont + "");
                name.setText(rentInfo.getName());
                relation.setText(rentInfo.getRelation());
//                Drawable updated = mContext.getResources().getDrawable(R.drawable.updated);
//                updated.setBounds(0, 0, 32, 32);
//                update.setImageDrawable(updated);
            }

        } else if (mType == 2) {
            TextView push = findTv(R.id.push_serve);
            SpannableString content = new SpannableString(mContext.getResources().getString(R.string.push_server));
            Drawable img = mContext.getResources().getDrawable(R.drawable.update_service);
            img.setBounds(0, 0, 40, 50);
            ImageSpan imageSpan = new ImageSpan(img);
            content.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            push.setText(content);
            push.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view.getId(), position);
                }
            });
        }
    }

    private void initTextColor(int color, TextView... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setTextColor(color);
        }
    }

    private void showContent(View operate, TextView operate_title) {
        operate.setVisibility(View.VISIBLE);
        operate_title.setVisibility(View.GONE);
    }

    private void showTitle(View operate, TextView operate_title) {
        operate.setVisibility(View.GONE);
        operate_title.setVisibility(View.VISIBLE);
    }

    private void initViewColor(int color, View... a) {
        for (int i = 0; i < a.length; i++) {
            a[i].setBackgroundColor(color);
        }
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}
