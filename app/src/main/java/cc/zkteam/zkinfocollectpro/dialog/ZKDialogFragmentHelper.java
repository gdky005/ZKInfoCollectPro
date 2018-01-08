package cc.zkteam.zkinfocollectpro.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.ZKBase;

/**
 * ZHDialogFragment 通用的对话框
 * Created by WangQing on 2017/12/22.
 */

public class ZKDialogFragmentHelper {

    private static final String TAG = "ZHDialogFragment";

    /**
     * 确定取消框
     */
    private static final int CONFIRM_THEME = R.style.Base_AlertDialog;
    private static final String CONfIRM_TAG = TAG + ":dialog";

    /**
     * 通用的 dialog
     */
    public static ZKDialogFragment showDialog(FragmentManager fragmentManager, final String title, final String content,
                                              final ZKDialogResultListener<Integer> listener, OnZKDialogCancelListener cancelListener) {
        Context context = ZKBase.getContext();
        return showDialog(fragmentManager, title, content,
                context.getString(R.string.dialog_default_sure_btn_text),
                context.getString(R.string.dialog_default_fail_btn_text),
                true, listener, cancelListener);
    }

    /**
     * 只有一个按钮的 dialog
     */
    public static ZKDialogFragment showSingleBtnDialog(FragmentManager fragmentManager, final String title, final String content,
                                                       final ZKDialogResultListener<Integer> listener, OnZKDialogCancelListener cancelListener) {
        Context context = ZKBase.getContext();
        String sureText = context.getString(R.string.dialog_single_sure_btn_text);

        return showDialog(fragmentManager, title, content, sureText, null, true, listener, cancelListener);
    }


    public static ZKDialogFragment showDialog(FragmentManager fragmentManager, final String title, final String content,
                                              String sureText, String failText, boolean cancelable,
                                              final ZKDialogResultListener<Integer> listener, OnZKDialogCancelListener cancelListener) {
        ZKDialogFragment dialogFragment = ZKDialogFragment.newInstance(new OnZKCallDialog() {
            @Override
            public Dialog getDialog(Context context) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, CONFIRM_THEME);
                LayoutInflater inflater = LayoutInflater.from(context);
                View rootView = inflater.inflate(R.layout.dialog_common, null);
                builder.setView(rootView);
                AlertDialog alertDialog = builder.create();

                TextView titleTV = rootView.findViewById(R.id.title);
                TextView contentTV = rootView.findViewById(R.id.content);
                Button sureBtn = rootView.findViewById(R.id.sure_btn);
                Button failBtn = rootView.findViewById(R.id.fail_btn);

                if (!TextUtils.isEmpty(title)) {
                    titleTV.setText(title);
                } else {
                    titleTV.setText("");
                }

                if (!TextUtils.isEmpty(content)) {
                    contentTV.setText(content);
                } else {
                    contentTV.setText("");
                }

                if (!TextUtils.isEmpty(sureText)) {
                    sureBtn.setText(sureText);
                } else {
                    sureBtn.setText(R.string.dialog_default_sure_btn_text);
                }


                if (!TextUtils.isEmpty(failText)) {
                    failBtn.setText(failText);
                    failBtn.setVisibility(View.VISIBLE);
                } else {
                    failBtn.setVisibility(View.GONE);
                }

                sureBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onDataResult(DialogInterface.BUTTON_POSITIVE);
                        }
                        alertDialog.dismiss();
                    }
                });

                failBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onDataResult(DialogInterface.BUTTON_NEGATIVE);
                        }
                        alertDialog.dismiss();
                    }
                });

                return alertDialog;
            }
        }, cancelable, cancelListener);
        dialogFragment.show(fragmentManager, CONfIRM_TAG);
        return dialogFragment;
    }

}
