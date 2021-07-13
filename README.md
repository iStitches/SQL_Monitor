# MyBatis插件——监听SQL并持久化记录

## 项目概述

当前市场中已经有许多针对MyBatis的日志记录插件，但是整体上体量太大，如果仅仅需要记录SQL语句等基础信息对资源的利用非常不划算，同时对开发效率也有一定的影响。因此为了巩固MyBatis的执行流程，开发了一款监听MyBatis 执行过程中的SQL记录的插件。

## 项目结构

<img src="E:/blog/source/passageImg/深入理解Java虚拟机.assets/image-20210713193248192.png" alt="image-20210713193248192" style="zoom: 67%;" />

主要结构上图所示：

* annotation：自定义的一些注解，方便在实现AOP时标注在需要的方法上；
* aspect：自定义AOP切面，实现对MyBatis异常的日志记录功能；
* config：配置类的包，主要包含 MyBatis、ThreadPool的配置；
* constants：常量类包，Common类和统一返回格式ResultObj类；
* controller：控制层；
* dao：数据持久层；
* service：服务层；
* entity：实体类；
* exception：异常类、全局异常捕获处理；
* interceptor：自定义拦截器，主要针对MyBatis的拦截；
* utils：工具包，主要包含配置文件解析工具、加密解密工具类；

## 对MyBatis的监控处理

通过资料查询，了解到可以通过重写MyBatis中的拦截器，**并设置在SqlSessionFactoryBean**中来实现拦截SQL的目的。

### MyBatis核心对象介绍

* Configuration：初始化基础配置，存储一些重要的对象，比如映射器、ObjectFactory和TypeFactory对象，存储了MyBatis中的所有配置对象；
* SqlSessionFactory：SqlSession工厂，使用工厂模式生成SqlSession对象；
* SqlSession：核心类，通过 getMapper("xxxx")方法获取到指定Mapper后进行操作处理，进而完成数据库增删改查功能，实际使用了动态代理进行增强处理；
* Executor：MyBatis的执行器，负责SQL语句生成和查询缓存的维护；
* StatementHandler：封装了JDBC Statement操作，负责对JDBC Statement的操作，比如设置参数、将Statement结果集转换为List集合；
* ParameterHandler：负责将用户传入的参数转化为JDBC Statement所需的参数；
* ResultSetHandler：负责将JDBC返回的ResultSet结果集转换为Java的List集合；
* TypeHandler：负责Java数据类型和JDBC数据类型之间的转换和映射；
* MappedStatement：维护一条SQL语句的封装处理；
* SqlSource：结合MappedStatement中的SQL语句对象，根据传递的参数动态生成SQL语句并封装在BoundSql对象中；
* BoundSql：动态生成的SQL语句以及相应的参数信息。

### MyBatis拦截实现流程

1. 获取到 `MappedStatement`对象来获得SQL语句对象的具体数据信息，然后解析其中得到`BoundSql`对象和`Configuration`对象。

2. 通过`BoundSql`对象获取到具体的SQL语句信息和参数信息，结合参数类型处理器`TypeHandler`逐个映射解析各个占位符对应什么参数并填充记录。
3. 参数的处理需要区分，如果SQL语句中只有一个占位符，获得的`parameterObject`就是一个直接的值；如果有多个占位符，就会以Map的结果呈现，需要分别处理。
4. 持久化到数据库。

### 拦截后信息记录

![image-20210713200523526](E:/blog/source/passageImg/深入理解Java虚拟机.assets/image-20210713200523526.png)

![image-20210713200504377](E:/blog/source/passageImg/深入理解Java虚拟机.assets/image-20210713200504377.png)

### 对敏感数据加密处理

敏感数据比如说用户密码、银行卡密码、电话号码等不能直接存储在数据库，需要加密处理，私钥存储在服务端。

加密方法使用了 `Cipher`类，该类能够实现对称加密`DES`、非对称加密`RSA`等等加密算法。详细学习可以看看这篇文章：

> https://www.cnblogs.com/caizhaokai/p/10944667.html

```java
/**
 * 加密解密工具类(DES)
 */
public class CryptUtil {
    /**
     * 当前秘钥
     */
    private static Key CURRENT_KEY;
    /**
     * 默认秘钥
     */
    private static final String DEFAULT_KEY = "1fsdfnaiuehi12423511e&&*332342*)";
    /**
     * 加密模式
     */
    private static final String ENCODE_MODE = "DES";
    /**
     * 加密解密格式
     */
    private static final String CODE_FORMAT = "DES/ECB/PKCS5Padding";

    static {
        CURRENT_KEY = getCurrentKey(DEFAULT_KEY);
    }

    /**
     * 获取当前密钥
     * @param key
     * @return
     */
    private static Key getCurrentKey(String key){
        if(key == null){
            return CURRENT_KEY;
        }
        Key ans = null;
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(ENCODE_MODE);
            // 防止随机种子生成的Key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes("UTF-8"));
            keyGenerator.init(secureRandom);
            ans = keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * 加密
     * @param msg
     * @return
     */
    public static String encode(String key, String msg){
         return Base64.encodeBase64URLSafeString(obtainEncode(key,msg.getBytes()));
    }

    /**
     * 底层加密方法
     * @param key
     * @param str
     * @return
     */
    private static byte[] obtainEncode(String key, byte[] str){
        byte[] ans = null;
        Cipher cipher;
        try {
            Key codeKey = getCurrentKey(key);
            cipher = Cipher.getInstance(CODE_FORMAT);
            cipher.init(Cipher.ENCRYPT_MODE,codeKey);
            ans = cipher.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return ans;
    }

    /**
     * 解密
     * @param str
     * @return
     */
    public static String decode(String key, String str){
         return new String(obtainDecode(key,str.getBytes()));
    }

    private static byte[] obtainDecode(String key, byte[] str){
        byte[] ans = null;
        Cipher cipher;
        try {
            Key codeKey = getCurrentKey(key);
            cipher = Cipher.getInstance(CODE_FORMAT);
            cipher.init(Cipher.DECRYPT_MODE,codeKey);
            ans = cipher.doFinal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }
}
```

以上是加密/解密工具类的定义，数据加密需要放在参数解析那里，根据正则匹配，如果发现参数名中存在敏感字符就将该参数的结果进行加密处理，然后再填充持久化。

![image-20210713201844005](E:/blog/source/passageImg/深入理解Java虚拟机.assets/image-20210713201844005.png)

### 配置随时开启/关闭 监控

由于MyBatis监控的开启是在配置`SqlSessionFactoryBean`对象时添加的，因此可以在这里处理。

1. 在主配置文件`application.yml`中定义一个`monitor`来表示监控开启和关闭；
2. 编写一个工具类，用来获取`yml`类型的配置文件中的参数信息；
3. 在项目启动时根据`monitor`的值来判断是否给`SqlSessionFactoryBean`添加上拦截器，判断是否开启监控，实现动态关闭开启监控。

![image-20210713202217291](E:/blog/source/passageImg/深入理解Java虚拟机.assets/image-20210713202217291.png)

解析`yml`文件的工具类：

```java
/**
 * 解析文件的工具类
 */
public class ConfigsUtil {
    // 默认文件(主配置文件)
    private static String DEFAULT_FILE = "application.yaml";
    // 一般键值对结果
    private static Map<String,Object> result = new HashMap<>();

    /**
     * 根据文件名获取yml的文件内容
     * @param file
     * @return
     */
    public static Map<String,Object> getYamlByFileName(String file){
        if(file == null){
            file = DEFAULT_FILE;
        }
        // 获取文件流
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        Yaml yaml = new Yaml();
        Object obj = yaml.loadAs(inputStream,Map.class);
        Map<String,Object> params = (Map<String, Object>)obj;
        // 解析结果
        for(Map.Entry<String,Object> entry:params.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            result.put(key,value);
        }
        return result;
    }

    /**
     * 获取指定字段的结果
     * @param key
     * @return
     */
    public static String getByValue(String fileName, String key){
        Map<String,Object> ans = getYamlByFileName(fileName);
        int a = 2;
        if(ans == null)
            return null;
        return ans.get(key).toString();
    }
}
```

动态开启关闭

![image-20210713202330267](E:/blog/source/passageImg/深入理解Java虚拟机.assets/image-20210713202330267.png)

### 监控流程异步

采用线程池方式实现监控记录和MyBatis执行异步。

线程池配置：

```java
@Configuration
public class ThreadPoolConfig {
    @Bean(value = "threadPoolInstance")
    public ExecutorService createThreadPoolInstance(){
        // 通过guava类库实现线程工厂并设置名称
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build();
        // 核心线程数10个；最大线程16个；非核心线程生存时间60秒；采用有界队列存储任务
        ExecutorService threadPool = new ThreadPoolExecutor(10,16,60L, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(100),new ThreadPoolExecutor.AbortPolicy());
        return threadPool;
    }
}
```

异步处理：

![image-20210713202613765](E:/blog/source/passageImg/深入理解Java虚拟机.assets/image-20210713202613765.png)

## 项目参考：

**springBoot 之MyBatis 添加拦截器 --输出完整SQL语句:**

http://www.javashuo.com/article/p-uxmpdijl-ca.html

**MyBatis 插件之拦截器（Interceptor）**

https://blog.csdn.net/weixin_39494923/article/details/91534658/

**循环依赖**

https://www.zhihu.com/question/438247718

**Java加密解密工具类**

https://blog.csdn.net/m0_37701381/article/details/80209109

**Spring Boot中优雅的获取yml文件工具类**

https://www.jb51.net/article/152235.htm

## 源码地址：

项目地址：`https://github.com/iStitches/SQL_Monitor`

​                                                                       **一个Star 一个Offer**

![img](E:/blog/source/passageImg/深入理解Java虚拟机.assets/image.biaobaiju.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg)