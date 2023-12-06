package com.ipusoft.context.view.dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.component.ToastUtils;
import com.ipusoft.context.component.base.BaseDialogFragment;
import com.ipusoft.context.databinding.ContextPermissionDialogStoragePermissionBinding;
import com.ipusoft.context.viewmodel.BaseViewModel;
import com.ipusoft.utils.AppUtils;

/**
 * author : GWFan
 * time   : 4/16/21 3:08 PM
 * desc   : 存储权限的Dialog
 */

public class StoragePermissionDialog extends BaseDialogFragment<ContextPermissionDialogStoragePermissionBinding, BaseViewModel> {
    private static final String TAG = "OverLayPermissionDialog";

    private PermissionCallBack storagePermissionListener;

    public static StoragePermissionDialog getInstance() {
        return new StoragePermissionDialog();
    }

    public interface PermissionCallBack {
        void invoke(boolean permission);
    }

    public StoragePermissionDialog setOnStoragePermissionListener(PermissionCallBack storagePermissionListener) {
        this.storagePermissionListener = storagePermissionListener;
        return this;
    }

    @Override
    protected void initUI() {
        super.initUI();
        String tip = "在通话结束后，我们会收集您通过连连微号外呼的通话录音。此功能需要您打开存储权限。";
        binding.tvMsg.setText(tip);
        binding.llAgree.setOnClickListener(this);
        binding.tvNotToUse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == binding.llAgree.getId()) {
            try {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:" + AppUtils.getAppPackageName()));
                        AppContext.getActivityContext().startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        AppContext.getActivityContext().startActivity(intent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showMessage("请手动打开");
            }
            dismissAllowingStateLoss();
        } else if (v.getId() == binding.tvNotToUse.getId()) {
            dismissAllowingStateLoss();
            if (storagePermissionListener != null) {
                storagePermissionListener.invoke(false);
            }
        }
    }
}