package com.ipusoft.context.view;

import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.databinding.DataBindingUtil;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.BaseActivity;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.R;
import com.ipusoft.context.bridge.NativeJSBridge;
import com.ipusoft.context.config.Env;
import com.ipusoft.context.databinding.ContextActivityIpuWebViewBinding;
import com.ipusoft.context.iface.IpuWebInterface;
import com.ipusoft.context.manager.PhoneManager;

/**
 * author : GWFan
 * time   : 5/13/21 10:02 AM
 * desc   :
 */

public class IpuWebViewActivity extends BaseActivity implements NativeJSBridge {
    private static final String TAG = "IpuWebViewActivity";
    private ContextActivityIpuWebViewBinding binding;


    @Override
    protected void initViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.context_activity_ipu_web_view);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initUI() {
        WebView webView = binding.webview;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new IpuWebInterface(this), "android");

        String url = "https://presaas.51lianlian.cn/h5/container.html?authCode="
                + IpuSoftSDK.getAuthCode() + "&type=SDK";
        if (AppContext.getRuntimeEnv() == Env.PRO) {
            url = "https://saas.51lianlian.cn/h5/container.html?authCode="
                    + IpuSoftSDK.getAuthCode() + "&type=SDK";
        }
        webView.loadUrl(url);
    }

    @Override
    protected void bindLiveData() {

    }

    @Override
    protected void initRequest() {

    }

    @Override
    public void call(String phone) {
        PhoneManager.callPhone(phone);
    }

    @Override
    public void goBack() {
        finish();
    }
}
