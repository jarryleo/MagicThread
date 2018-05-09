# MagicThread
安卓端、纯注解使用的线程切换框架

### how to use
example:
```
    @RunOnIOThread
    private void test() {
        String name = Thread.currentThread().getName();
        Log.e("current thread name:", name);
    }
```
### 框架提供3种线程注解：

> @RunOnUIThread    (被注解的方法在UI线程执行)

> @RunOnIOThread    (被注解的方法在子线程执行，适合耗时操作)

> @RunOnBackGround  (被注解的方法在后台线程执行，所有被注解的方法都在同一个线程，队列执行，不适合耗时操作)


### To get a Git project into your build:

#### Step 1. Add the JitPack repository to your build file
1.在全局build里面添加下面github仓库地址
Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
	  }
}
```
#### Step 2. Add the dependency
2.在app的build里面添加插件，依赖，和插件的仓库地址
```
apply plugin: 'cn.leo.plugin.magic'
...
dependencies {
	  implementation 'com.github.jarryleo:MagicThread:v1.0'
}
//plugin repository
buildscript {
    repositories {
        jcenter()
        maven{ url 'https://dl.bintray.com/jarryleo/maven'}
    }
    dependencies {
        classpath 'cn.leo.plugin:magic-plugin:1.0.0'
    }
}
```
