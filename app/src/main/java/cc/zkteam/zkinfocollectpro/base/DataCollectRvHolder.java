package cc.zkteam.zkinfocollectpro.base;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.bean.RentInfo;

/**
 * Created by Administrator on 2017/12/15.
 */

public class DataCollectRvHolder extends RvHolder<RentInfo> {

    private int type;

    public DataCollectRvHolder(View itemView, int type, RvListener listener, int type1) {
        super(itemView, type, listener);
    }

    public DataCollectRvHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);

        this.type = type;
    }

    @Override
    public void bindHolder(RentInfo rentInfo, int position) {


    }


    public TextView findTv(int id){
        return ((TextView) itemView.findViewById(id));
    }

    public Button findBtn(int id){
        return ((Button) itemView.findViewById(id));
    }

    @Override
    public void bindHolder(List<RentInfo> data, final int position) {


        if (type == 0) {
            Button btn = findBtn(R.id.update_person);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view.getId(),position);
                }
            });
        } else if (type == 1) {
            final int rePosiont = position - 1;
            RentInfo rentInfo = data.get(rePosiont);


            TextView num = findTv(R.id.num);
            TextView name = findTv(R.id.name);
            TextView relation = findTv(R.id.relation);
            TextView update = findTv(R.id.update);
            TextView operate_title = findTv(R.id.operate_title);
            TextView caiji = findTv(R.id.caiji);
            TextView out = findTv(R.id.out);

            caiji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view.getId(),rePosiont);
                }
            });

            out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view.getId(),rePosiont);
                }
            });

            View operate = itemView.findViewById(R.id.operate);

            if (rePosiont == 0) {
                showTitle(operate, operate_title);
                initTextColor(Color.parseColor("#ffffff"), num, name, relation, update, operate_title);
                initViewColor(Color.parseColor("#3ba3d0"), num, name, relation, update, operate, operate_title);
            } else if (rePosiont % 2 == 0) {      //偶数行
                initTextColor(Color.parseColor("#333333"), num, name, relation, update, caiji, out);
                showContent(operate, operate_title);
                initViewColor(Color.parseColor("#ebf6fa"), num, name, relation, update, operate);
            } else {     //奇数行
                initTextColor(Color.parseColor("#333333"), num, name, relation, update, caiji, out);
                showContent(operate, operate_title);
                initViewColor(Color.parseColor("#dff0f7"), num, name, relation, update, operate);
            }

            operate_title.setText(rentInfo.operate);
            num.setText(rentInfo.num);
            name.setText(rentInfo.name);
            relation.setText(rentInfo.relation);
            update.setText(rentInfo.update);

        } else if (type == 2) {
            findBtn(R.id.push_serve).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view.getId(),position);
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
}
