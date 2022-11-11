# Common
SDK的基础库，集成功能模块的SDK之前需要先集成该基础模块。
## Setup
### gradle
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    //使用时把 latest-version 替换成最新release版本
    implementation 'com.github.ipulian:ipusdk:latest-version'
}
### maven
```maven
<repositories>
    <repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
	 <groupId>com.github.ipulian</groupId>
	 <artifactId>ipusdk</artifactId>
	 <version>Tag</version>
</dependency>
```
```xml
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_NUMBERS" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
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
        //env 标识运行时环境，有两种Env.OPEN_DEV 对应预发布环境，Env.OPEN_PRO 对应正式环境
        IpuSoftSDK.init(this,env);//之后通过IpuSoftSDK.updateAuthInfo()更新 AuthInfo
        //OR
        IpuSoftSDK.init(this,env,new AuthInfo("key","secret","username"));
        //OR 如果是SIP外呼，用这种方式
        IpuSoftSDK.init(this,env,new AuthInfo("key","secret","username","password"));
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
  IpuSoftSDK.registerPhoneStatusChangedListener(new OnPhoneStatusChangedListenerImpl());
```
3. 实现弹屏
```java
  //初始化WindowManager
  WindowManager mWindowManager = IWindowManager.getWindowManager();
  //初始化LayoutParams
  WindowManager.LayoutParams mLayoutParams = IWindowManager.getWindowParams();
  //把弹屏的自定义View 添加到Winddow中
  mWindowManager.addView(view, mLayoutParams);
```
- 4.查看通话记录，使用拨号键盘，查看电话统计，客户统计，综合排名等功能，可以跳转到内部的H5页面
```java
  startActivity(new Intent(this, IpuWebViewActivity.class));
```
## Change Log

#### v1.6.39
* 优化使用体验

#### v1.6.38
* 支持SIP通话的回电

## ProGuard rules
```pro
-keep class com.ipusoft.context.bean.** { *;}
-keep class com.ipusoft.context.constant.** { *;}
```
# License
```
MIT License

Copyright (c) 2021 ipulian

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

