package com.ipusoft.context.component.base;

import android.content.Intent;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import com.ipusoft.context.component.ToastUtils;
import com.ipusoft.context.viewmodel.BaseViewModel;
import com.ipusoft.logger.XLogger;
import com.ipusoft.utils.ExceptionUtils;

/**
 * author : GWFan
 * time   : 8/11/20 5:47 PM
 * desc   : 实现懒加载的Fragment基类
 */

public abstract class BaseObserverDialogFragment<VB extends ViewDataBinding, VM extends BaseViewModel> extends BaseDialogFragment<VB, VM> {
    private static final String TAG = "BaseObserverDialogFragm";

    @Override
    protected void initData() {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void bindLiveData() {
        initObserver();
    }

    private void initObserver() {
        try {
            if (vm != null) {
                /*
                 * 显示LoadingView
                 */
                vm.showLoadingLiveData.observe(getViewLifecycleOwner(), o -> ToastUtils.showLoading());
                /*
                 * 显示MsgLoadingView
                 */
                vm.showMsgLoadingLiveData.observe(getViewLifecycleOwner(), ToastUtils::showLoading);
                /*
                 * 显示Toast
                 */
                vm.showToastLiveData.observe(getViewLifecycleOwner(), ToastUtils::showMessage);

                /*
                 * 页面跳转
                 */
                vm.navigateLiveData.observe(getViewLifecycleOwner(), this::startActivity);

                vm.navigateLiveData2.observe(getViewLifecycleOwner(),
                        clazz -> startActivity(new Intent(getActivity(), clazz)));

                /*
                 * 退出当前页面
                 */
                vm.finishLiveData.observe(getViewLifecycleOwner(), o -> {
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        activity.finish();
                    }
                });

                /*
                 * 隐藏Dialog
                 */
                vm.dismissLiveData.observe(getViewLifecycleOwner(), o -> dismissAllowingStateLoss());
            }
        } catch (Exception e) {
            XLogger.e(TAG + "->" + ExceptionUtils.getErrorInfo(e));
        }
    }
}