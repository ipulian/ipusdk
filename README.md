# ipusdk
SDk的基础库，集成功能模块的SDK，需要先集成该基础模块。
## Setup
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.ipulian:ipusdk:latest-version'
}
```
```xml
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_NUMBERS" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
        <!--悬浮窗-->
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.SYSTEM_OVERLAY_WINDOW" />
```
## Usage
1. 初始化
```java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        IpuSoftSDK.init(this);//之后通过IpuSoftSDK.updateAuthInfo()更新 IAuthInfo
        //OR
        IpuSoftSDK.init(this,new IAuthInfo("key","secret","username"));
    }
}
```
2. 监听通话状态,需要实现 OnPhoneStateChangedListener 接口
```java
public class OnPhoneStatusChangedListenerImpl implements OnPhoneStateChangedListener {
    @Override
    public void onDialingListener() {
         //响铃
         //在这个地方可以展示外呼弹屏
    }

    @Override
    public void onInComingListener() {
       //呼入
        //在这个地方可以展示呼入弹屏
    }

    @Override
    public void onConnectedListener() {
      //接通
    }

    @Override
    public void onDisConnectedListener() {
     //挂断
      //在这个地方可以展示挂机弹屏
    }
}
```
并把该接口的实现类 OnPhoneStatusChangedListenerImpl 注册到 SDK中
```java
   //该方法在Application的onCreate中调用
  IpuSoftSDK.setOnPhoneStatusChangedListener(new OnPhoneStatusChangedListenerImpl());
```
3. 实现弹屏
```java
  //初始化WindowManager
  WindowManager mWindowManager = IWindowManager.getWindowManager(context);
  //初始化LayoutParams
  WindowManager.LayoutParams mLayoutParams = IWindowManager.getFullWidthWindowParams();
  //把弹屏的自定义View 添加到Winddow中
  mWindowManager.addView(view, mLayoutParams);
```
