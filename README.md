# creekrouter_for_android
# 一、简介
用于解决Android组件化、插件化问题。一套方案应对组件化、插件化两种场景。     
该代码仓库中，SourceCode分支为框架源码。     
组件化代码示例：切换本仓库分支为Demo分支。     
插件化代码示例：<a href="https://github.com/creekrouter/demo_for_creek_router" >CreekRouter插件化代码</a>     

# 二、基本配置
1. 配置文件。
```text
项目根目录下添加一个配置文件，文件名为：CreekRouter.xml
内容如下：
<?xml version="1.0" encoding="UTF-8"?>
<!--    根节点至少包含一个AppModule属性,属性值为项目的Application Module名称-->
<!--    group为分组，可以不写。-->
<!--    Log标签、Module标签 选填-->
<Router AppModule="app" group="main">
    <Log dir="./app/build" />
    <Module name="app" aliasName="app_module" />
</Router>

```
2. gradle配置。
```text
maven地址配置：
   maven {
       url 'https://creekrouter.com/maven'
   }

gradle依赖配置：
   implementation 'com.creek.router:creekrouter:1.3.0'
   annotationProcessor 'com.creek.router:creekrouter:1.3.0'

```

# 三、组件化基本使用
假设A.class和B.class位于不同的Module当中。     
其中，A.class
```java
import android.content.Context;
import android.content.Intent;
import com.creek.router.annotation.CreekMethod;

public class A {
    @CreekMethod(path = "start_activity")
    public boolean startActivity(Context context, String param) {
        if (context == null || param == null || param.length() == 0) {
            return false;
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("param", param);
        context.startActivity(intent);
        return true;
    }
}
```
B.class中test()方法调用A.class的startActivity方法：
```java
import android.content.Context;
import com.creek.router.CreekRouter;

public class B {
    public boolean test(Context context, String param) {
        boolean result = CreekRouter.methodRun("start_activity", "hello world!");
        return result;
    }
}

```
也可以这样调用：
```java

public interface TestInterface {
    @CreekMethod(path = "start_activity")
    boolean launch(Context context, String param);
}

public class B {
    public boolean test(Context context, String param) {
        TestInterface test = CreekRouter.create(TestInterface.class);
        return test.launch(context, param);
    }
}

```
# 四、插件化
<a href="https://creekrouter.com/files/plugin.mp4">插件化演示视频:2MB Apk加载20MB插件apk，插件apk也可独立运行</a>     
插件化代码示例：<a href="https://github.com/creekrouter/demo_for_creek_router" >插件化基础包代码</a>、<a href="https://github.com/creekrouter/CreekEmail" >插件化包代码</a>
