package cc.zkteam.zkinfocollectpro.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/30.
 */

public class CreateHouseActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_person_location)
    TextView mPersonLocation;
    @BindView(R.id.et_ownership_certificate_no)
    EditText mOwnershipCertificateNo;
    @BindView(R.id.et_housing_property)
    EditText mHousingProperty;
    @BindView(R.id.et_land_use)
    EditText mLandUse;
    @BindView(R.id.et_land_no)
    EditText mLandNo;
    @BindView(R.id.et_covered_area)
    EditText mCoveredArea;
    @BindView(R.id.et_indoor_area)
    EditText mIndoorArea;
    @BindView(R.id.tv_select_register_date)
    TextView mSelectRegisterDate;
    @BindView(R.id.et_house_type)
    EditText mHouseType;
    @BindView(R.id.rb_house_vacancy)
    RadioButton mHouseVacancy;
    @BindView(R.id.rb_house_since)
    RadioButton mHouseSince;
    @BindView(R.id.rb_house_for_rent)
    RadioButton mHouseForRent;
    @BindView(R.id.rb_house_commercial)
    RadioButton mHouseCommercial;
    @BindView(R.id.rb_house_for_other)
    RadioButton mHouseForOther;
    @BindView(R.id.rg_house_status)
    RadioGroup mHouseStatus;
    @BindView(R.id.et_property_owner_name)
    EditText mPropertyOwnerName;
    @BindView(R.id.rb_property_owner_man)
    RadioButton mPropertyOwnerMan;
    @BindView(R.id.rb_property_owner_female)
    RadioButton mPropertyOwnerFemale;
    @BindView(R.id.rg_property_owner_sex)
    RadioGroup mPropertyOwnerSex;
    @BindView(R.id.rb_identity_card)
    RadioButton mIdentityCard;
    @BindView(R.id.rb_driving_license)
    RadioButton mDrivingLicense;
    @BindView(R.id.rb_business_license)
    RadioButton mBusinessLicense;
    @BindView(R.id.rb_other_type_of_certificate_for_property_owner)
    RadioButton mOtherTypeOfCertificateForPropertyOwner;
    @BindView(R.id.rg_property_owner_type_of_certificate)
    RadioGroup mPropertyOwnerTypeOfCertificate;
    @BindView(R.id.et_property_owner_certificate)
    EditText mPropertyOwnerCertificate;
    @BindView(R.id.rb_is_house_owner)
    RadioButton mIsHouseOwner;
    @BindView(R.id.rb_is_not_house_owner)
    RadioButton mIsNotHouseOwner;
    @BindView(R.id.rg_property_owner_is_house_owner)
    RadioGroup mPropertyOwnerIsHouseOwner;
    @BindView(R.id.et_property_owner_registered_residence)
    EditText mPropertyOwnerRegisteredResidence;
    @BindView(R.id.et_property_owner_present_address)
    EditText mPropertyOwnerPresentAddress;
    @BindView(R.id.et_property_owner_work_unit)
    EditText mPropertyOwnerWorkUnit;
    @BindView(R.id.et_property_owner_phone)
    EditText mPropertyOwnerPhone;
    @BindView(R.id.et_co_owner_name)
    EditText mCoOwnerName;
    @BindView(R.id.rb_co_owner_man)
    RadioButton mCoOwnerMan;
    @BindView(R.id.rb_co_owner_female)
    RadioButton mCoOwnerFemale;
    @BindView(R.id.rg_co_owner_sex)
    RadioGroup mCoOwnerSex;
    @BindView(R.id.rb_co_owner_identity_card)
    RadioButton mCoOwnerIdentityCard;
    @BindView(R.id.rb_co_owner_driving_license)
    RadioButton mCoOwnerDrivingLicense;
    @BindView(R.id.rb_co_owner_business_license)
    RadioButton mCoOwnerBusinessLicense;
    @BindView(R.id.rb_other_type_of_certificate_for_co_owner)
    RadioButton mOtherTypeOfCertificateForCoOwner;
    @BindView(R.id.rg_co_owner_type_of_certificate)
    RadioGroup mCoOwnerTypeOfCertificate;
    @BindView(R.id.et_co_owner_certificate)
    EditText mCoOwnerCertificate;
    @BindView(R.id.rb_co_is_house_owner)
    RadioButton mCoIsHouseOwner;
    @BindView(R.id.rb_co_is_not_house_owner)
    RadioButton mCoIsNotHouseOwner;
    @BindView(R.id.rg_co_owner_is_house_owner)
    RadioGroup mCoOwnerIsHouseOwner;
    @BindView(R.id.et_co_owner_registered_residence)
    EditText mCoOwnerRegisteredResidence;
    @BindView(R.id.et_co_owner_present_address)
    EditText mCoOwnerPresentAddress;
    @BindView(R.id.et_co_owner_work_unit)
    EditText mCoOwnerWorkUnit;
    @BindView(R.id.et_co_owner_phone)
    EditText mCoOwnerPhone;
    @BindView(R.id.btn_save_and_commit)
    Button mSaveAndCommitBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_house;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListener() {
        mSaveAndCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHouseAndDataPretreatment();
            }
        });
    }

    private void createHouseAndDataPretreatment() {
        if (inputIsEmpty(mCoveredArea, "请输入建筑面积...")
                || inputIsEmpty(mIndoorArea, "请输入室内面积...")
                || inputIsEmpty(mSelectRegisterDate, "请选择登记日期...")
                || inputIsEmpty(mHouseType, "请输入户型...")
                || inputIsEmpty(mHouseStatus, "请选择房屋状态...")
                || inputIsEmpty(mPropertyOwnerName, "请输入产权人姓名...")
                || inputIsEmpty(mPropertyOwnerTypeOfCertificate, "请选择产权人证件类型...")
                || inputIsEmpty(mPropertyOwnerCertificate, "请输入产权人证件号...")
                || inputIsEmpty(mPropertyOwnerIsHouseOwner, "请选择产权人是否房主...")
                || inputIsEmpty(mPropertyOwnerPhone, "请输入产权人联系电话...")) {
            return;
        }
        System.out.println("");
    }

    private boolean inputIsEmpty(View view, String msg) {
        if (view instanceof EditText) {
            if (TextUtils.isEmpty(((EditText) view).getText().toString())) {
                showToast(msg);
                return true;
            }
        }
        if (view instanceof RadioGroup) {
            LogUtils.e("" +((RadioGroup) view).getCheckedRadioButtonId());
            if (((RadioGroup) view).getCheckedRadioButtonId() == -1) {
                showToast(msg);
                return true;
            }
        }
        return false;
    }

    private void showToast(String msg) {
        ToastUtils.cancel();
        ToastUtils.showShort(msg);
    }


    @Override
    protected void initData() {

    }
}
