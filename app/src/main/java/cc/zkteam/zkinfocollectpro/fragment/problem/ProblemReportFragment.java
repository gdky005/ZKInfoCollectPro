package cc.zkteam.zkinfocollectpro.fragment.problem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.blankj.utilcode.util.ToastUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.MapActivity;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.fragment.problem.mvp.PRPresenterImpl;
import cc.zkteam.zkinfocollectpro.fragment.problem.mvp.PRView;
import cc.zkteam.zkinfocollectpro.managers.ZHConfigDataManager;
import cc.zkteam.zkinfocollectpro.utils.L;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static android.app.Activity.RESULT_OK;
import static cc.zkteam.zkinfocollectpro.activity.IDCardScanActivity.REQUEST_IMAGE;

/**
 * Created by Administrator on 2017/12/15.
 */

public class ProblemReportFragment extends BaseFragment implements PRView {
    private static final int GO_MAP = 1;
    private static final String PROBLEM_EDIT_FLAG = "problem_edit_flag";
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
    @BindView(R.id.et_problem_suggestion)
    EditText mProblemSuggestion;
    @BindView(R.id.btn_commit)
    Button mCommitBtn;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.et_problem_attachment)
    EditText mProblemAttachment;
    @BindView(R.id.btn_select_pic)
    Button mSelectPicBtn;
    @BindView(R.id.pb_loading)
    ProgressBar mLoading;
    private PRPresenterImpl mPresenter;
    private boolean mIsEditPage = false;
    private String mCurrPicPath = "";

    public static ProblemReportFragment newInstance(boolean isEdit) {
        Bundle args = new Bundle();
        args.putBoolean(PROBLEM_EDIT_FLAG, isEdit);
        ProblemReportFragment fragment = new ProblemReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsEditPage = getArguments().getBoolean(PROBLEM_EDIT_FLAG);
        Log.e("TAG", "" + mIsEditPage);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_problem_report;
    }

    @Override
    public void initView(View rootView) {
        initSpinner();
        isPageCanEdit();
    }

    private void isPageCanEdit() {
        if (mIsEditPage) {
            mToolbarTitle.setText("问题信息填写");
        } else {
            showProblemDetail();
        }
    }

    private void showProblemDetail() {
        mToolbarTitle.setText("问题信息详情");
        initToolbar(mToolbar);
        forbidClick();
        judgeAndShowProblemDetail();
        mSelectLocationBtn.setVisibility(View.GONE);
        mCommitBtn.setVisibility(View.GONE);
    }

    protected void initToolbar(Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //  设置了左上角的返回按钮
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    private void judgeAndShowProblemDetail() {
        mProblemSource.setText("无数据");
        mProblemDesc.setText("无数据");
        mProblemLocation.setText("无数据");
        mProblemSuggestion.setText("无数据");
    }

    private void forbidClick() {
        mProblemSource.setFocusable(false);
        mProblemDesc.setFocusable(false);
        mProblemLocation.setFocusable(false);
        mProblemSuggestion.setFocusable(false);
        mProblemType.setEnabled(false);
    }

    private void initSpinner() {
        //  建立数据源
        String[] items = new String[ZHConfigDataManager.getInstance().getWtsbData().size()];
        ZHConfigDataManager.getInstance().getWtsbData().toArray(items);
        //  建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.item_problem_type, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  绑定 Adapter到控件
        mProblemType.setAdapter(adapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter = new PRPresenterImpl(this);
        if (mIsEditPage) {
            mPresenter.loadData();
        }
    }

    @Override
    public void initListener() {
        mSelectLocationBtn.setOnClickListener(v -> {
//                PageCtrl.startActivity(MapActivity.class);
            startActivityForResult(new Intent(getActivity(),
                    MapActivity.class), GO_MAP);
        });
        mCommitBtn.setOnClickListener(v -> judgeInput());
        mSelectPicBtn.setOnClickListener(v -> MultiImageSelector.create()
                .showCamera(false)
                .single() // single mode
                .start(ProblemReportFragment.this, REQUEST_IMAGE));
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
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> pics = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

                if (pics != null && pics.size() > 0) {
                    String galleyPicPath = pics.get(0);
                    L.d("当前图片地址是：" + galleyPicPath);
                    mCurrPicPath = galleyPicPath;
                    String path[] = galleyPicPath.split("/");
                    mProblemAttachment.setText(path[path.length - 1]);
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
                || inputIsEmpty(mProblemAttachment, R.string.select_problem_pic)
                || inputIsEmpty(mProblemSuggestion, R.string.input_problem_suggestion)) {
            return;
        }
        showLoading();
        mPresenter.report(mProblemSource.getText().toString(),
                ZHConfigDataManager.getInstance().getWtsbType(mProblemType.getSelectedItem().toString()),
                mProblemDesc.getText().toString(),
                mProblemLocation.getText().toString(),
                mProblemAttachment.getText().toString(),
                mProblemSuggestion.getText().toString(),
                mCurrPicPath);
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

    @Override
    public void cleanInput() {
        mProblemSource.setText("");
        mProblemDesc.setText("");
        mProblemAttachment.setText("");
        mProblemSuggestion.setText("");
    }

    @Override
    public void showLoading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoading.setVisibility(View.GONE);
    }
}
