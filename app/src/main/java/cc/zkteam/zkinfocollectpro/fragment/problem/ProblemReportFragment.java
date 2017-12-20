package cc.zkteam.zkinfocollectpro.fragment.problem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.baidu.mapapi.search.core.PoiInfo;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.MapActivity;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.fragment.problem.mvp.PRPresenterImpl;
import cc.zkteam.zkinfocollectpro.fragment.problem.mvp.PRView;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/12/15.
 */

public class ProblemReportFragment extends BaseFragment implements PRView {
    private static final int GO_MAP = 1;
    @BindView(R.id.et_problem_source)
    EditText mProblemSource;
    @BindView(R.id.sp_problem_type)
    Spinner mProblemType;
    @BindView(R.id.et_problem_desc)
    EditText mProblemDesc;
    @BindView(R.id.et_problem_location)
    EditText mProblemLocation;
    @BindView(R.id.btn_select_location)
    Button mSelectLocationBtn;
    @BindView(R.id.rv_pic)
    RecyclerView mPicListView;
    @BindView(R.id.et_problem_suggestion)
    EditText mProblemSuggestion;
    @BindView(R.id.btn_commit)
    Button mCommitBtn;
    private PRPresenterImpl mPresenter;

    public static ProblemReportFragment newInstance() {

        Bundle args = new Bundle();

        ProblemReportFragment fragment = new ProblemReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_problem_report;
    }

    @Override
    public void initView(View rootView) {
        initSpinner();
    }

    private void initSpinner() {
        //  建立数据源
        String[] mItems = getResources().getStringArray(R.array.problem_type);
        //  建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.item_problem_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  绑定 Adapter到控件
        mProblemType.setAdapter(adapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter = new PRPresenterImpl(this);
        mPresenter.loadData();
    }

    @Override
    public void initListener() {
        mSelectLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PageCtrl.startActivity(MapActivity.class);
                startActivityForResult(new Intent(getActivity(),
                        MapActivity.class), GO_MAP);
            }
        });
        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgeInput();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GO_MAP) {
            if (data != null) {
                PoiInfo poiInfo = data.getParcelableExtra(MapActivity.NEW_LOCATION);
                if (poiInfo != null) {
                    mProblemLocation.setText(poiInfo.address);
                }
            }
        }
    }

    /**
     * 判断输入的值是否有效
     */
    private void judgeInput() {
        if (inputIsEmpty(mProblemSource, R.string.input_problem_source)
                || inputIsEmpty(mProblemType, R.string.input_problem_type)
                || inputIsEmpty(mProblemDesc, R.string.input_problem_desc)
                || inputIsEmpty(mProblemLocation, R.string.input_problem_location)
                || inputIsEmpty(mProblemSuggestion, R.string.input_problem_suggestion)) {
            return;
        }

        mPresenter.loadData();
    }

    private boolean inputIsEmpty(View view, int msgId) {
        if (view instanceof EditText) {
            if (TextUtils.isEmpty(((EditText) view).getText().toString())) {
                showToast(msgId);
                return true;
            }
        }
        if (view instanceof Spinner) {
            if (TextUtils.isEmpty(((Spinner) view).getSelectedItem().toString())) {
                showToast(msgId);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onLoading() {

    }


    private void showToast(int msgId) {
        ToastUtils.cancel();
        ToastUtils.showShort(msgId);
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
    public void setLocationInfo(String location) {
        mProblemLocation.setText(location);
    }
}
