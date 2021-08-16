package com.ipusoft.localcall.component;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.R;
import com.ipusoft.utils.ThreadUtils;
import com.ipusoft.localcall.datastore.SimDataRepo;

/**
 * author : GWFan
 * time   : 4/16/21 3:08 PM
 * desc   : 如何打开通话录音的Dialog
 */

public class HowToOpenRecordingDialog extends DialogFragment {
    protected View view;

    public static HowToOpenRecordingDialog getInstance() {
        Bundle args = new Bundle();
        HowToOpenRecordingDialog fragment = new HowToOpenRecordingDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void show() {
        long lastShowCheckRecordingPermissionDialog = SimDataRepo.getLastShowCheckRecordingPermissionDialog();
        long l = System.currentTimeMillis();
        if (l - lastShowCheckRecordingPermissionDialog >= 24 * 60 * 60 * 1000) {
            SimDataRepo.setLastShowCheckRecordingPermissionDialog(l);
            ThreadUtils.runOnUiThread(() -> {
                FragmentManager fm = AppContext.getActivityContext().getSupportFragmentManager();
                String tag = getClass().getSimpleName();
                Fragment prev = fm.findFragmentByTag(tag);
                if (prev != null) {
                    fm.beginTransaction().remove(prev);
                }
                // HowToOpenRecordingDialog.super.show(fm, tag);
                fm.beginTransaction().add(this, tag).commitAllowingStateLoss();
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            view = inflater.inflate(R.layout.context_dialog_how_to_open_recording, container);
            setCancelable(false);
            initView();
        }
        return view;
    }

    protected void initView() {
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("我们无法检测到通话录音文件，请检查通话录音权限是否打开");
        view.findViewById(R.id.iv_close).setOnClickListener(v -> dismissAllowingStateLoss());
    }
}
