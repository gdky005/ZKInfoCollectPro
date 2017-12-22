package cc.zkteam.zkinfocollectpro.dialog.test;

import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import java.util.Calendar;

import cc.zkteam.zkinfocollectpro.ZKBase;
import cc.zkteam.zkinfocollectpro.dialog.OnZKDialogCancelListener;
import cc.zkteam.zkinfocollectpro.dialog.ZKCommonDialogFragmentHelper;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogResultListener;

/**
 * Created by WangQing on 2017/12/22.
 */

public class TestDialogManager {

    FragmentManager getSupportFragmentManager;

    private static TestDialogManager instance = null;

    private TestDialogManager(FragmentManager getSupportFragmentManager) {
        this.getSupportFragmentManager = getSupportFragmentManager;
    }

    public static TestDialogManager getInstance(FragmentManager getSupportFragmentManager) {
        if (instance == null) {
            synchronized (TestDialogManager.class) {
                TestDialogManager temp = instance;
                if (temp == null) {
                    temp = new TestDialogManager(getSupportFragmentManager);
                    instance = temp;
                }
            }
        }
        return instance;
    }

//    以下为测试代码：
    /**
     * 选择时间的弹出窗
     */
    public void showTimeDialog() {
        String titleTime = "请选择时间";
        Calendar calendarTime = Calendar.getInstance();
        ZKCommonDialogFragmentHelper.showTimeDialog(getSupportFragmentManager, titleTime, calendarTime, new ZKDialogResultListener<Calendar>() {
            @Override
            public void onDataResult(Calendar result) {
                showToast(String.valueOf(result.getTime().getDate()));
            }
        }, true);
    }

    /**
     * 输入密码的弹出窗
     */
    public void showPasswordInsertDialog() {
        String titlePassword = "请输入密码";
        ZKCommonDialogFragmentHelper.showPasswordInsertDialog(getSupportFragmentManager, titlePassword, new ZKDialogResultListener<String>() {
            @Override
            public void onDataResult(String result) {
                showToast("密码为：" + result);
            }
        }, true);
    }

    /**
     * 显示列表的弹出窗
     */
    public void showListDialog() {
        String titleList = "选择哪种方向？";
        final String [] languanges = new String[]{"Android", "iOS", "web 前端", "Web 后端", "老子不打码了"};

        ZKCommonDialogFragmentHelper.showListDialog(getSupportFragmentManager, titleList, languanges, new ZKDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {
                showToast(languanges[result]);
            }
        }, true);
    }

    public void showInsertDialog() {
        String titleInsert  = "请输入想输入的内容";
        ZKCommonDialogFragmentHelper.showInsertDialog(getSupportFragmentManager, titleInsert, new ZKDialogResultListener<String>() {
            @Override
            public void onDataResult(String result) {
                showToast(result);
            }
        }, true);
    }

    /**
     * 选择日期的弹出窗
     */
    public void showDateDialog() {
        String titleDate = "请选择日期";
        Calendar calendar = Calendar.getInstance();
        ZKCommonDialogFragmentHelper.showDateDialog(getSupportFragmentManager, titleDate, calendar, new ZKDialogResultListener<Calendar>() {
            @Override
            public void onDataResult(Calendar result) {
                showToast(String.valueOf(result.getTime().getDate()));
            }
        }, true);
    }

    /**
     * 确认和取消的弹出窗
     */
    public void showConfirmDialog() {
        ZKCommonDialogFragmentHelper.showConfirmDialog(getSupportFragmentManager, "是否选择 Android？", new ZKDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {
                showToast("You Click Ok：" + result);
            }
        }, true, new OnZKDialogCancelListener() {
            @Override
            public void onCancel() {
                showToast("You Click Cancel");
            }
        });
    }


    /**
     * 对 Toast 进行封藏，减少代码量
     *
     * @param message 想要显示的信息
     */
    public void showToast(String message){
        Toast.makeText(ZKBase.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
