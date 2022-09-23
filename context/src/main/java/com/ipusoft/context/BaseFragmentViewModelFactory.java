package com.ipusoft.context;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * author : GWFan
 * time   : 7/30/21 2:16 PM
 * desc   : ViewModelFactory的基类
 */

public class BaseFragmentViewModelFactory implements ViewModelProvider.Factory {
    private static final String TAG = "BaseViewModelFactory";
    private Bundle bundle;

    public BaseFragmentViewModelFactory() {
    }

    public BaseFragmentViewModelFactory(Bundle bundle) {
        this.bundle = bundle;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> clazz) {
        T t = null;
        if (bundle != null) {
            try {
                Constructor<T> constructor = clazz.getConstructor(Bundle.class);
                t = constructor.newInstance(bundle);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
                try {
                    Constructor<T> constructor = clazz.getConstructor();
                    t = constructor.newInstance();
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            try {
                Constructor<T> constructor = clazz.getConstructor();
                t = constructor.newInstance();
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return t;
    }
}
