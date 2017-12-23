package cc.zkteam.zkinfocollectpro.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;

/**
 * ZKDialogFragment 通用 Dialog
 * Created by WangQing on 2017/12/22.
 */

public class ZKDialogFragment extends DialogFragment {


    /**
     * 监听弹出窗是否被取消
     */
    private OnZKDialogCancelListener cancelListener;

    /**
     * 回调获得需要显示的 dialog
     */
    private OnZKCallDialog callDialog;

    public static ZKDialogFragment newInstance(OnZKCallDialog callDialog, boolean cancelable) {
        return newInstance(callDialog, cancelable, null);
    }


    public static ZKDialogFragment newInstance(OnZKCallDialog callDialog, boolean cancelable, OnZKDialogCancelListener cancelListener) {
        ZKDialogFragment instance = new ZKDialogFragment();
        instance.setCancelable(cancelable);
        instance.callDialog = callDialog;
        instance.cancelListener = cancelListener;
        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (null == callDialog) {
            return super.onCreateDialog(savedInstanceState);
        }
        return callDialog.getDialog(getActivity());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            // 在 5.0 以下的版本会出现白色背景边框，若在 5.0 以上设置则会造成文字部分的背景也变成透明
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                // 目前只有这两个 dialog 会出现边框
                if (dialog instanceof ProgressDialog || dialog instanceof DatePickerDialog) {
                    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }

            Window window = getDialog().getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            window.setAttributes(windowParams);
        }
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (cancelListener != null) {
            cancelListener.onCancel();
        }
    }
}
