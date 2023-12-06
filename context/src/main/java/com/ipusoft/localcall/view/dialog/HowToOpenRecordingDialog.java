package com.ipusoft.localcall.view.dialog;

import android.content.Intent;
import android.view.View;

import com.ipusoft.context.component.base.BaseDialogFragment;
import com.ipusoft.context.databinding.ContextDialogHowToOpenRecordingBinding;
import com.ipusoft.utils.SysRecordingUtils;
import com.ipusoft.context.viewmodel.BaseViewModel;
import com.ipusoft.localcall.datastore.SimDataRepo;

/**
 * author : GWFan
 * time   : 4/16/21 3:08 PM
 * desc   : 如何打开通话录音的Dialog
 */

public class HowToOpenRecordingDialog extends BaseDialogFragment<ContextDialogHowToOpenRecordingBinding, BaseViewModel> {
    private static final String TAG = "HowToOpenRecordingDialo";

    public static HowToOpenRecordingDialog getInstance() {
        return new HowToOpenRecordingDialog();
    }

    @Override
    protected void initUI() {
        super.initUI();
        binding.ivClose.setOnClickListener(v -> dismissAllowingStateLoss());
        binding.tvToOpen.setOnClickListener(this);
    }

    @Override
    public void show() {
        long lastShowCheckRecordingPermissionDialog = SimDataRepo.getLastShowCheckRecordingPermissionDialog();
        long l = System.currentTimeMillis();
        if (l - lastShowCheckRecordingPermissionDialog >= 30 * 60 * 1000) {
            SimDataRepo.setLastShowCheckRecordingPermissionDialog(l);
            super.show();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == binding.tvToOpen.getId()) {
            if (SysRecordingUtils.isHUAWEI()) {
                SysRecordingUtils.startHuaweiRecord();
            } else if (SysRecordingUtils.isMIUI()) {
                SysRecordingUtils.startXiaomiRecord();
            } else if (SysRecordingUtils.isOPPO()) {
                SysRecordingUtils.startOppoRecord();
            } else if (SysRecordingUtils.isVIVO()) {
                SysRecordingUtils.startViVoRecord();
            } else {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                startActivity(intent);
            }
        }
    }
}