### base_network
 
 **App 网络请求框架**；
 目前包括内容：暂无更多介绍。
 
 
### 关于网络请求框架封装
 
**选型：**
 
首先将所有的网络请求框架或客户端工具等列出来，如下：
- HttpClient
- HttpUrlConnection
- Volley
- OkHttp
- Retrofit
 
其他小众或常用框架，有些也设计比较好，或多或少都有参考价值并且也都不错：

- AsyncHttpClient
- NoHttp
- OkGo
- OkHttpUtils
- XUtils
- ...

首先，作为最早接触使用最广的客户端接口 HttpClient和HttpUrlConnection，他们在android的早期版本中广泛使用，因此有必要
先对他们做一些介绍。二者都支持Https协议，以流的形式进行上传和下载，配置超时时间，Ipv6，连接池等。
Apache的HttpClient，集成于Android的sdk中，功能丰富接口众多，一般使用DefaultHttpClient和AndroidHttpClient来进行
网络请求，然而也是由于HttpClient的Api数量过多，扩展上就显得麻烦，保持兼容性对他进行扩展就比较成为一件相当耗费人力的工作。
反观Jdk中的HttpUrlConnection接口，所提供的api很少，功能强大使用简单，但正由于此，Google工程师倾向于对他的扩展使用。
具体可参考[Android访问网络，使用HttpURLConnection还是HttpClient？](http://blog.csdn.net/guolin_blog/article/details/12452307)

随后，在2013Google I/O大会上出现了Volley，实际上它内部使用的还是HttpClient(android 9之前)和HttpUrlConnection（
android 9及之后），它是Google专门为Android app请求网络设计的Http库，使用简单，速度快。
参考[Volley官网介绍](https://developer.android.com/training/volley/index.html)，
[源码地址](https://github.com/google/volley)
Volley目前存在诸多问题，但大部分基于接口的设计使得它扩展起来较为方便，可以作为后续选择的一种。

OkHttp - An HTTP+HTTP/2 client for Android and Java applications.Square公司开源的OkHttp，
[官网介绍](http://square.github.io/okhttp/), [源码](https://github.com/square/okhttp)

**优点：**
- 支持SPDY，连接池，传输效率的各种优化
- 支持Android 2.3 ，Java 最低版本要求是 1.7;
- 支持Gzip压缩；
- 支持Https
- 强大的缓存机制

鉴于OkHttp强大的特性及Android最新源码底层也采用OkHttp，我们优先考虑使用OkHttp来作为目前网络请求框架。
其他，如Retrofit-基于注解的类型安全的框架，AsyncHttpClient因为基于HttpClient，停止维护，很显然的区别就是，OkHttp和
HttpClient及HttpUrlConnection类似是基于Http协议的请求客户端，其他如Volley，AsyncHttpClient，Volley，Retrofit等，
是在前面三者的基础上封装的网络请求框架。Volley框架封装和扩展性都比较好，可扩展底层使用OkHttp请求网络，Retrofit默认使用
OkHttp进行请求，但解耦更好更彻底，因此使用OkHttp首先要考虑在OkHttp基础上进行封装，如果不采用Volley和Retrofit的话，
封装需要有一定的水平才行。

Retrofit实际上常见的是Retrofit和RxJava结合使用，Retrofit使用时，如果服务端设计api比较符合Restful规范，使用上比较方便。
若结合RxJava进行使用，则无往而不利。

 
**版本选择：**

当前OkHttp最新版本是 3.9.0（20171116），未专门研究当前版本稳定性，未发现版本新增特性及缺陷。
对OkHttp框架的封装，开源方案有OkGo-jeasonlzy/okhttp-OkGo[OkGo](https://github.com/jeasonlzy/okhttp-OkGo),
hongyangAndroid/okhttputils，停止维护[OkHttpUtils](https://github.com/hongyangAndroid/okhttputils)

 
**以后要注意的点：**

。。。
 
 
### 项目整体结构

整体结构如下：
  
>**base_network**

```
┌─.gradle/
├─.idea/
├─app/
├─build/
├─gradle/
├─.gitignore
├─build.gradle
├─gradle.properties
├─gradlew
├─gradlew.bat
├─local.properties
├─README.md
├─settings.gradle
└─...
```

>**组件化模块划分**

```
┌base
├─base_network          // 网络请求模块
├─base_utils            // 工具类库 
├─base_components       // 组件库
├─...                   // 其他
├─...
├─...
└─...
```

关于网络请求框架的功能，第一步可能考虑到功能的实现，有开源的网络请求框架可以参考，是否从零开始写，需要讨论下。



### 

### 修改记录

- 设计雏形，高晓辉，20171116；

### 参考

1. [android httpClient和URLConnection的区别](http://blog.csdn.net/u012049463/article/details/24487593)
2. [Android访问网络，使用HttpURLConnection还是HttpClient？](http://blog.csdn.net/guolin_blog/article/details/12452307)
2. [okhttp,retrofit,android-async-http,volley应该选择哪一个？](https://www.zhihu.com/question/35189851?sort=created)
2. [设计模式-策略设计模式](http://blog.csdn.net/jtaizlx0102/article/details/50975519)








