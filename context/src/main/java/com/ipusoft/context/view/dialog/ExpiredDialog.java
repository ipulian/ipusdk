package com.ipusoft.context.view.dialog;

import com.ipusoft.context.component.base.BaseDialogFragment;
import com.ipusoft.context.databinding.ContextDialogExpiredBinding;
import com.ipusoft.context.iface.OnButtonClickListener;
import com.ipusoft.context.viewmodel.BaseViewModel;


/**
 * author : GWFan
 * time   : 9/12/20 11:47 AM
 * desc   :身份过期
 */

public class ExpiredDialog extends BaseDialogFragment<ContextDialogExpiredBinding, BaseViewModel> {
    private static final String TAG = "ExpiredDialog";

    private OnButtonClickListener listener;

    private static class ExpiredDialogHolder {
        private static final ExpiredDialog INSTANCE = new ExpiredDialog();
    }

    public static ExpiredDialog getInstance() {
        return new ExpiredDialog();
    }

    public ExpiredDialog setOnOkClickListener(OnButtonClickListener listener) {
        this.listener = listener;
        return this;
    }

    public void show() {
        if (isAdded() || isVisible() || isShowing) {
            return;
        }
        super.show();
    }

    @Override
    protected void initUI() {
        binding.llOk.setOnClickListener(v -> listener.onButtonClick(binding.llOk));
    }
}
