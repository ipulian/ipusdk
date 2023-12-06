package com.ipusoft.context.view.dialog;

import android.view.View;

import com.ipusoft.context.component.base.BaseDialogFragment;
import com.ipusoft.context.databinding.ContextLayoutAlertDialogBinding;
import com.ipusoft.context.viewmodel.BaseViewModel;
import com.ipusoft.utils.StringUtils;


/**
 * author : GWFan
 * time   : 2020/5/19 14:35
 * desc   :
 */

public class IDialog extends BaseDialogFragment<ContextLayoutAlertDialogBinding, BaseViewModel> {

    private String title, msg, middleBtnText, confirmText, cancelText;

    private OnConfirmClickListener confirmClickListener;

    private OnCancelClickListener cancelClickListener;

    private OnMiddleBtnClickListener middleBtnClickListener;

    private boolean showCancelBtn = true, showMiddleBtn = false;

    public interface OnConfirmClickListener {
        void onConfirm();
    }

    public interface OnCancelClickListener {
        void onCancel();
    }

    public interface OnMiddleBtnClickListener {
        void onMiddleBtnClick();
    }

    public static IDialog getInstance() {
        return new IDialog();
    }

    public IDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public IDialog setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public IDialog setOnConfirmClickListener(OnConfirmClickListener listener) {
        this.confirmClickListener = listener;
        return this;
    }

    public IDialog setOnCancelClickListener(OnCancelClickListener listener) {
        this.cancelClickListener = listener;
        return this;
    }

    public IDialog setOnMiddleBtnClickListener(OnMiddleBtnClickListener listener) {
        this.middleBtnClickListener = listener;
        return this;
    }

    public IDialog setShowCancelBtn(boolean showCancelBtn) {
        this.showCancelBtn = showCancelBtn;
        return this;
    }

    public IDialog setShowMiddleBtn(boolean showMiddleBtn) {
        this.showMiddleBtn = showMiddleBtn;
        return this;
    }

    public IDialog setSetMiddleText(String middleText) {
        this.middleBtnText = middleText;
        return this;
    }

    public IDialog setConfirmText(String text) {
        this.confirmText = text;
        return this;
    }

    public IDialog setCancelText(String text) {
        this.cancelText = text;
        return this;
    }

    @Override
    protected boolean hideNavigationBar() {
        return true;
    }

    @Override
    protected void initUI() {
        super.initUI();
        if (StringUtils.isNotEmpty(title)) {
            binding.tvTitle.setText(title);
        }
        binding.tvMsg.setText(msg);
        if (StringUtils.isNotEmpty(confirmText)) {
            binding.tvConfirm.setText(confirmText);
        }
        if (StringUtils.isNotEmpty(cancelText)) {
            binding.tvCancel.setText(cancelText);
        }
        if (showCancelBtn) {
            binding.tvCancel.setVisibility(View.VISIBLE);
        } else {
            binding.tvCancel.setVisibility(View.GONE);
        }
        if (showMiddleBtn) {
            binding.llMiddle.setVisibility(View.VISIBLE);
        } else {
            binding.llMiddle.setVisibility(View.GONE);
        }
        if (StringUtils.isNotEmpty(middleBtnText)) {
            binding.tvMiddle.setText(middleBtnText);
        }
        binding.tvCancel.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);
        binding.llMiddle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == binding.tvCancel.getId()) {
            dismissAllowingStateLoss();
            if (cancelClickListener != null) {
                cancelClickListener.onCancel();
            }
        } else if (v.getId() == binding.tvConfirm.getId()) {
            dismissAllowingStateLoss();
            if (confirmClickListener != null) {
                confirmClickListener.onConfirm();
            }
        } else if (v.getId() == binding.llMiddle.getId()) {
            dismissAllowingStateLoss();
            if (middleBtnClickListener != null) {
                middleBtnClickListener.onMiddleBtnClick();
            }
        }
    }
}
