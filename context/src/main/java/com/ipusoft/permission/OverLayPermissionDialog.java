package com.ipusoft.permission;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.R;
import com.ipusoft.context.utils.ScreenUtils;

/**
 * author : GWFan
 * time   : 4/16/21 3:08 PM
 * desc   : 申请悬浮窗权限的Dialog
 */

public class OverLayPermissionDialog extends DialogFragment {
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1024;

    protected static FragmentActivity mActivity;

    private PermissionCallBack layPermissionListener;

    protected View view;

    public interface PermissionCallBack {
        void invoke(boolean permission);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog mDialog = getDialog();
        if (mDialog != null) {
            Window window = mDialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.gravity = Gravity.CENTER;
                attributes.width = 17 * ScreenUtils.getAppScreenWidth() / 24;
                attributes.height = ScreenUtils.getAppScreenHeight() / 3;
                window.setAttributes(attributes);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            view = inflater.inflate(R.layout.context_permission_dialog_overlay_permission, container);
            setCancelable(false);
            initView();
        }
        return view;
    }

    public static OverLayPermissionDialog getInstance(FragmentActivity activity) {
        OverLayPermissionDialog.mActivity = activity;
        Bundle args = new Bundle();
        OverLayPermissionDialog fragment = new OverLayPermissionDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public OverLayPermissionDialog setOnOverLayPermissionListener(PermissionCallBack layPermissionListener) {
        this.layPermissionListener = layPermissionListener;
        return this;
    }

    public void show() {
        show(getClass().getSimpleName());
    }

    public void show(String tag) {
        mActivity.runOnUiThread(() -> {
            FragmentManager fm = mActivity.getSupportFragmentManager();
            Fragment prev = fm.findFragmentByTag(tag);
            if (prev != null) {
                fm.beginTransaction().remove(prev);
            }
            OverLayPermissionDialog.super.show(fm, tag);
        });
    }

    protected void initView() {
        TextView tvMsg = view.findViewById(R.id.tv_msg);
        String tip = "在通话过程中，我们会弹窗展示客户信息，此功能需要您打开悬浮窗权限。";
        tvMsg.setText(tip);

        view.findViewById(R.id.ll_agree).setOnClickListener(v -> {
            startActivityForResult(
                    new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + AppContext.getAppContext().getPackageName())),
                    OVERLAY_PERMISSION_REQUEST_CODE);
            dismiss();
        });

        view.findViewById(R.id.tv_not_to_use).setOnClickListener(v -> {
            dismiss();
            if (layPermissionListener != null) {
                layPermissionListener.invoke(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "onActivityResult: -------");
        //打开悬浮窗权限结果返回
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (layPermissionListener != null) {
                layPermissionListener.invoke(true);
            }
        }
    }
}
