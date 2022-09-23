package com.ipusoft.context.component.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.BaseFragmentViewModelFactory;
import com.ipusoft.context.R;
import com.ipusoft.context.viewmodel.BaseViewModel;
import com.ipusoft.logger.XLogger;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.ThreadUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * author : GWFan
 * time   : 8/7/21 4:56 PM
 * desc   :
 */

@Keep
public abstract class BaseDialogFragment<VB extends ViewDataBinding, VM extends BaseViewModel> extends DialogFragment
        implements View.OnClickListener {
    private static final String TAG = "BaseDialogFragment";

    protected AppCompatActivity mActivity;

    protected VB binding;

    protected VM vm;
    /**
     * ViewDataBinding
     */
    protected Class<VB> vbClazz = null;

    protected boolean isShowing = false;

    public void show() {
        show(System.currentTimeMillis() + "");
    }

    protected void show(String tag) {
        mActivity = AppContext.getActivityContext();
        if (mActivity != null) {
            ThreadUtils.runOnUiThread(() -> {
                try {
                    FragmentManager fm = mActivity.getSupportFragmentManager();
                    Fragment prev = fm.findFragmentByTag(tag);
                    if (prev != null) {
                        isShowing = false;
                        fm.beginTransaction().remove(prev).commitAllowingStateLoss();
                    }
                    if (isAdded() || isVisible() || isShowing) {
                        isShowing = false;
                        fm.beginTransaction().remove(this).commitAllowingStateLoss();
                    }
                    fm.beginTransaction().add(this, tag).commitAllowingStateLoss();
                    isShowing = true;
                } catch (Exception e) {
                    XLogger.e(TAG + "->show：" + ExceptionUtils.getErrorInfo(e));
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            Dialog dialog = getDialog();
            if (dialog != null) {
                Window window = dialog.getWindow();
                if (window != null) {
                    WindowManager.LayoutParams attributes = window.getAttributes();
                    attributes.gravity = Gravity.CENTER;
                    attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                    attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
                    window.setAttributes(attributes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.IDialogFragmentStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            Class<VM> vmClazz = null;
            Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            if (actualTypeArguments.length != 0) {
                for (Type type : actualTypeArguments) {
                    Class<?> aClass = Class.forName(type.toString().split(" ")[1]);
                    if (BaseViewModel.class.isAssignableFrom(aClass)) {
                        vmClazz = (Class<VM>) type;
                    } else if (ViewDataBinding.class.isAssignableFrom(aClass)) {
                        vbClazz = (Class<VB>) type;
                    }
                }
            }
            Method method = vbClazz.getMethod("inflate", LayoutInflater.class);
            binding = (VB) method.invoke(null, inflater);
            if (vmClazz != null) {
                vm = new ViewModelProvider(this, new BaseFragmentViewModelFactory(getArguments())).get(vmClazz);
                Method initMethod = null;
                try {
                    initMethod = vbClazz.getDeclaredMethod("setVm", vmClazz);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (initMethod != null) {
                    initMethod.setAccessible(true);
                    initMethod.invoke(binding, vm);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (binding != null) {
            binding.setLifecycleOwner(getViewLifecycleOwner());
            if (getDialog() != null) {
                getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            }

            initData();

            //创建ActivityLauncher
            createIntent();

            initUI();

            initRequest();

            bindLiveData();

            View rootView = binding.getRoot();
            boolean clickable = rootView.isClickable();
            if (clickable) {
                rootView.setOnClickListener(this);
            }
            return rootView;
        }
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog() != null) {
//            if (shouldInitImmersionBar() || hideNavigationBar()) {
//                if (hideNavigationBar()) {
//                    ImmersionBar.with(this)
//                            .keyboardEnable(true)
//                            .fullScreen(false)
//                            .statusBarDarkFont(statusBarDarkFont())
//                            .autoDarkModeEnable(true)
//                            .autoStatusBarDarkModeEnable(true)
//                            .autoNavigationBarDarkModeEnable(true)
//                            .navigationBarColor(getNavigationBarColor())
//                            .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
//                            .init();
//                } else {
//                    initImmersionBar();
//                }
//            }
        }
    }

//    protected void initImmersionBar() {
//        ImmersionBar.with(this)
//                .keyboardEnable(true)
//                .fullScreen(false)
//                .statusBarDarkFont(statusBarDarkFont())
//                .autoDarkModeEnable(true)
//                .autoStatusBarDarkModeEnable(true)
//                .autoNavigationBarDarkModeEnable(true)
//                .navigationBarColor(getNavigationBarColor())
//                .init();
//    }

    /**
     * 状态栏字体颜色
     *
     * @return 是否黑色字体
     */
    protected boolean statusBarDarkFont() {
        return false;
    }

    @ColorRes
    protected int getNavigationBarColor() {
        return R.color.navigationBarColor;
    }

    protected boolean shouldInitImmersionBar() {
        return true;
    }

    protected boolean hideNavigationBar() {
        return false;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isShowing = false;
    }

    @Override
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
        isShowing = false;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        //   ImmersionBar.destroy(this);
    }

    protected void initData() {
    }

    protected void createIntent() {
    }

    protected void initUI() {
    }

    protected void initRequest() {

    }

    protected void bindLiveData() {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.getRoot().getId()) {
            dismissAllowingStateLoss();
        }
    }
}
