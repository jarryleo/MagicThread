# MagicThread
安卓端、纯注解使用的线程切换框架

### how to use
#### 一个注解搞定线程切换

example:
```
    @RunOnIOThread
    public void progress() {
        for (int i = 0; i <= 100; i++) {
            showProgress(i);
            SystemClock.sleep(1000);
        }
    }

    @RunOnUIThread
    private void showProgress(int progress) {
        mTvTest.setText(progress + "%");
    }
```
### 框架提供4种线程注解：

> @RunOnUIThread    (被注解的方法在UI线程执行)

> @RunOnIOThread    (被注解的方法在子线程执行，适合IO密集型耗时操作(文件读写,网络请求))

> @RunOnCalcThread  (被注解的方法在子线程执行，适合计算密集型耗时操作(大量消耗CPU的计算))

> @RunOnBackGround  (被注解的方法在后台线程执行，所有被注解的方法都在同一个线程，队列执行，不适合耗时操作)

> @RunOnUIThread 和 @RunOnBackGround 增加参数,为延时执行,单位毫秒值

### 依赖方法:
#### To get a Git project into your build:
#### Step 1. Add the JitPack repository to your build file
1.在全局build里面添加下面github仓库地址
Add it in your root build.gradle at the end of repositories:
```
buildscript {
    ...
    dependencies {
	...
        classpath 'cn.leo.plugin:magic-plugin:1.0.0' //java 用这个
	classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.0' //kotlin 用这个
    }
}
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
google()和jcenter()这两个仓库一般是默认的，如果没有请加上

#### Step 2. Add the dependency
2.在app的build里面添加插件和依赖
```
apply plugin: 'cn.leo.plugin.magic' //java 用这个
apply plugin: 'android-aspectjx'  //kotlin 用这个，编译速度会慢点
...
dependencies {
	...
	implementation 'com.github.jarryleo:MagicThread:v2.3'
}
```


> 用于支持kotlin的插件用的是 [aspectjx](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx)   
> 感谢插件作者    
> 因为编织所有二进制文件的问题导致编译速度慢的问题，请查看原作者提供的解决方案    

### 关于子线程在activity和fragment中进行耗时操作导致的内存泄漏，本框架提供解决办法：

在耗时操作的循环体中加入以下代码：
```
if (Thread.currentThread().isInterrupted()) return;
```
> 如果是采用休眠的耗时操作，请在捕获InterruptedException异常后跳出循环

#### 注意：
只在注解 @RunOnIOThread 的子线程中有效，利用了安卓新特性Lifecycle

其它2个注解不适合做耗时操作，不做处理

##### 请勿把注解打在有返回值的方法上，否则会失效

### 小贴士

在 app 的 build 依赖里再加一个依赖：

```
implementation 'com.github.jarryleo:MagicPermission:v1.3'
```
## 即可使用安卓纯注解动态权限申请框架

在需要权限的方法上打上注解，全自动处理动态权限各种问题：

自动处理用户同意/拒绝操作；

自动处理用户拒绝并勾选不在提示后的 弹框提示，并跳转到设置界面引导用户开启权限；

用户在设置界面返回后自动处理  设置界面操作的结果；

兼容国产rom；

#### 使用示例：

单个权限申请
```
@PermissionRequest(Manifest.permission.CAMERA)
public void testPermission() {
        //执行权限申请通过后的业务逻辑
}
```

多个权限同时申请
```
@PermissionRequest({Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
public void testPermission() {
        //执行权限申请通过后的业务逻辑
}
```
详情见 [安卓纯注解动态权限申请库](https://github.com/jarryleo/MagicPermission)

