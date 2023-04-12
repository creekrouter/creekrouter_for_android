# creekrouter_for_android
# 一、简介
```text
用于解决Android组件化、插件化问题。一套方案应对组件化、插件化两种场景。

```

# 二、基本配置
1. 配置文件。
```text
项目根目录下添加一个配置文件，文件名为：CreekRouter.xml
内容如下：
<?xml version="1.0" encoding="UTF-8"?>
<!--    根节点至少包含一个AppModule属性,属性值为项目的Application Module名称-->
<!--    group为分组，可以不写。-->
<!--    Log标签、Module标签 选填-->
<Router AppModule="mail" group="mail">
    <Log dir="./mail/build" />
    <Module name="mail" aliasName="MailCore" />
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


# 四、demo示例
