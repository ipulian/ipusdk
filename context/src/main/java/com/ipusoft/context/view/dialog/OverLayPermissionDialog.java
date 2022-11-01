package com.ipusoft.context.view.dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.component.ToastUtils;
import com.ipusoft.context.component.base.BaseDialogFragment;
import com.ipusoft.context.databinding.ContextPermissionDialogOverlayPermissionBinding;
import com.ipusoft.context.manager.PlatformManager;
import com.ipusoft.context.platform.OPPO;
import com.ipusoft.context.viewmodel.BaseViewModel;
import com.ipusoft.mmkv.datastore.CommonDataRepo;

/**
 * author : GWFan
 * time   : 4/16/21 3:08 PM
 * desc   : 申请悬浮窗权限的Dialog
 */

public class OverLayPermissionDialog extends BaseDialogFragment<ContextPermissionDialogOverlayPermissionBinding, BaseViewModel> {
    private static final String TAG = "OverLayPermissionDialog";

    private PermissionCallBack layPermissionListener;

    public interface PermissionCallBack {
        void invoke(boolean permission);
    }

    public static OverLayPermissionDialog getInstance() {
        return new OverLayPermissionDialog();
    }

    public OverLayPermissionDialog setOnOverLayPermissionListener(PermissionCallBack layPermissionListener) {
        this.layPermissionListener = layPermissionListener;
        return this;
    }

    @Override
    protected void initUI() {
        super.initUI();
        String tip = "在通话过程中，我们会弹窗展示客户信息，此功能需要您打开悬浮窗权限。";
        binding.tvMsg.setText(tip);
        binding.llAgree.setOnClickListener(this);
        binding.tvNotToUse.setOnClickListener(this);
        binding.tvNeverAnswer.setOnClickListener(this);
    }

    @Override
    public void show() {
        if (!CommonDataRepo.getNeverAnswerPermission()) {
            super.show();
        } else {
            if (layPermissionListener != null) {
                layPermissionListener.invoke(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == binding.llAgree.getId()) {
            try {
                if (PlatformManager.isOPPO()) {
                    OPPO.settingOverlayPermission(mActivity);
                    dismiss();
                } else {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + AppContext.getAppContext().getPackageName())));
                        dismiss();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showMessage("请手动打开");
            }
            dismissAllowingStateLoss();
        } else if (v.getId() == binding.tvNotToUse.getId()) {
            if (layPermissionListener != null) {
                layPermissionListener.invoke(true);
            }
            dismissAllowingStateLoss();
        } else if (v.getId() == binding.tvNeverAnswer.getId()) {
            CommonDataRepo.setNeverAnswerPermission(true);
            if (layPermissionListener != null) {
                layPermissionListener.invoke(true);
            }
            dismissAllowingStateLoss();
        }
    }
}