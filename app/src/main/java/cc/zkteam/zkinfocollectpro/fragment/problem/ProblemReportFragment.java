package cc.zkteam.zkinfocollectpro.fragment.problem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.MapActivity;
import cc.zkteam.zkinfocollectpro.activity.home.HomeActivity;
import cc.zkteam.zkinfocollectpro.adapter.ProblemPicAdapter;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.ProblemPreview;
import cc.zkteam.zkinfocollectpro.fragment.problem.mvp.PRPresenterImpl;
import cc.zkteam.zkinfocollectpro.fragment.problem.mvp.PRView;
import cc.zkteam.zkinfocollectpro.managers.ZHConfigDataManager;
import cc.zkteam.zkinfocollectpro.viewholder.ProblemPreviewHolder;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static android.app.Activity.RESULT_OK;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
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
    @BindView(R.id.ll_spinner)
    LinearLayout mSpinnerLayout;
    @BindView(R.id.ll_desc)
    LinearLayout mDescLayout;
    @BindView(R.id.rl_show_pics)
    RecyclerView mShowPics;
    @BindView(R.id.hsv_pic)
    HorizontalScrollView mShowPicsLayout;
    private PRPresenterImpl mPresenter;
    private boolean mIsEditPage = false;
    private ArrayList<String> mCurrPicPath = new ArrayList<>();
    private ProblemPreview.DataBean mProblem;

    public static ProblemReportFragment newInstance(boolean isEdit) {
        Bundle args = new Bundle();
        args.putBoolean(PROBLEM_EDIT_FLAG, isEdit);
        ProblemReportFragment fragment = new ProblemReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(boolean isEdit, ProblemPreview.DataBean problem) {
        Bundle args = new Bundle();
        args.putBoolean(PROBLEM_EDIT_FLAG, isEdit);
        args.putParcelable(ProblemPreviewHolder.PROBLEM_DETAIL_FLAG, problem);
        ProblemReportFragment fragment = new ProblemReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsEditPage = getArguments().getBoolean(PROBLEM_EDIT_FLAG);
        mProblem = getArguments().getParcelable(ProblemPreviewHolder.PROBLEM_DETAIL_FLAG);
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
        mProblemSource.setFocusable(false);
        mProblemSource.setText("采集端");
        mProblemSource.setBackground(null);
    }

    private void isPageCanEdit() {
        if (mIsEditPage) {
            mToolbarTitle.setText("问题信息填写");
        } else {
            mToolbarTitle.setText("问题信息详情");
            showProblemDetail();
        }
    }

    private void showProblemDetail() {
        initToolbar(mToolbar);
        forbidClick();
        hideSomeView();
        judgeAndShowProblemDetail();
        setBackground();
        reSizeDescLayout();
    }

    private void hideSomeView() {
        mSelectLocationBtn.setVisibility(View.GONE);
        mCommitBtn.setVisibility(View.GONE);
        mSelectPicBtn.setVisibility(View.GONE);
        mProblemAttachment.setVisibility(View.GONE);
    }

    private void reSizeDescLayout() {
        ViewGroup.LayoutParams lp = mDescLayout.getLayoutParams();
        lp.height = WRAP_CONTENT;
        mDescLayout.setLayoutParams(lp);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.item_problem_type, new String[]{TextUtils.isEmpty(mProblem.getType()) ? "无数据" :
                ZHConfigDataManager.getInstance().getWtsbValue(mProblem.getType())});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  绑定 Adapter到控件
        mProblemType.setAdapter(adapter);
        mProblemDesc.setText(TextUtils.isEmpty(mProblem.getProblemcontent()) ? "无数据" : mProblem.getProblemcontent());
        mProblemAttachment.setText(TextUtils.isEmpty(mProblem.getPath()) ? "无数据" : mProblem.getPath());
        mProblemLocation.setText(TextUtils.isEmpty(mProblem.getProblemposition()) ? "无数据" : mProblem.getProblemposition());
        mProblemSuggestion.setText(TextUtils.isEmpty(mProblem.getRemarks()) ? "无数据" : mProblem.getRemarks());

        List<String> pics = new ArrayList<>();
        pics.addAll(Arrays.asList(mProblem.getPath().split("[|]")));
        Log.e("TAG", pics.toString());
        if (pics.isEmpty() || "".equals(pics.get(0))) {
            mShowPicsLayout.setVisibility(View.GONE);
            mProblemAttachment.setText("无附件");
            mProblemAttachment.setVisibility(View.VISIBLE);
        } else {
            mShowPicsLayout.setVisibility(View.VISIBLE);
            mShowPics.setLayoutManager(new GridLayoutManager(getContext(), pics.size()));
            mShowPics.setAdapter(new ProblemPicAdapter(getContext(), pics, (id, position) -> {}));
        }
    }

    private void setBackground() {
        mProblemType.setBackground(null);
        mSpinnerLayout.setBackground(null);
        mProblemDesc.setBackground(null);
        mProblemLocation.setBackground(null);
        mProblemAttachment.setBackground(null);
        mProblemSuggestion.setBackground(null);
    }

    private void forbidClick() {
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
        mPresenter = new PRPresenterImpl(this, getContext());
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
                .showCamera(true)
                .count(5)
                .origin(mCurrPicPath)
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
                //  获取返回的图片列表
                List<String> pics = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

                if (pics != null && pics.size() > 0) {
                    mCurrPicPath.clear();
                    mCurrPicPath.addAll(pics);
                    mProblemAttachment.setText("");
                    for (int i = 0; i < pics.size(); i++) {
                        if (i > 4) {
                            return;
                        }
                        String galleyPicPath = pics.get(i);
                        String path[] = galleyPicPath.split("/");
                        mProblemAttachment.append(path[path.length - 1]);
                        if (i < pics.size() - 1) {
                            mProblemAttachment.append("\n");
                        }
                    }
                }
            }
        }
    }

    /**
     * 判断输入的值是否有效
     */
    private void judgeInput() {
        if (inputIsEmpty(mProblemType, R.string.input_problem_type)
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
        mProblemDesc.setText("");
        mProblemAttachment.setText("");
        mProblemSuggestion.setText("");
        if (getActivity() != null && ((HomeActivity) getActivity()).getViewPager() != null) {
            ((HomeActivity) getActivity()).getViewPager().setCurrentItem(HomeActivity.NAV_TYPE_MAIN);
        }
    }

    @Override
    public void showLoading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoading.setVisibility(View.GONE);
        mCurrPicPath.clear();
    }
}
