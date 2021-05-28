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
    implementation 'com.github.ipulian:ipusdk:1.6.13.5'
}
```
## Usage
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
