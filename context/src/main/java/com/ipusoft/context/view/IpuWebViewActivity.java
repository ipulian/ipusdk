package com.ipusoft.context.view;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ipusoft.context.AppRuntimeContext;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.R;
import com.ipusoft.context.bridge.NativeJSBridge;
import com.ipusoft.context.component.ToastUtils;
import com.ipusoft.context.config.Env;
import com.ipusoft.context.databinding.ContextActivityIpuWebViewBinding;
import com.ipusoft.context.iface.IpuWebInterface;
import com.ipusoft.context.manager.PhoneManager;
import com.ipusoft.utils.StringUtils;

/**
 * author : GWFan
 * time   : 5/13/21 10:02 AM
 * desc   :
 */

public class IpuWebViewActivity extends AppCompatActivity implements NativeJSBridge {
    private static final String TAG = "IpuWebViewActivity";
    private ContextActivityIpuWebViewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.context_activity_ipu_web_view);
        initUI();
    }

    protected void initUI() {
        WebView webView = binding.webview;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new IpuWebInterface(this), "android");

        String url = "https://presaas.51lianlian.cn/h5/container.html?authCode="
                + IpuSoftSDK.getAuthCode() + "&type=SDK";
        if (StringUtils.equals(Env.OPEN_PRO, AppRuntimeContext.getRuntimeEnv())) {
            url = "https://saas.51lianlian.cn/h5/container.html?authCode="
                    + IpuSoftSDK.getAuthCode() + "&type=SDK";
        }
        webView.loadUrl(url);
    }

    @Override
    public void call(String type, String phone) {
        if (StringUtils.equals("SIP", type)) {
            ToastUtils.showMessage("系统功能维护中");
        } else {
            PhoneManager.callPhone(phone);
        }
    }

    @Deprecated
    @Override
    public void call(String phone) {
        PhoneManager.callPhone(phone);
    }


    @Override
    public void goBack() {
        finish();
    }
}
