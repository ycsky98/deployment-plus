# deployment-plus
热部署插件deployment-plus,支持文件动态热编译

##使用方式:
    
    package com.deployment;

    import com.deployment.polling.filepolling.ClassLoaderActuator;

    /**
    * Unit test for simple App.
    */
    public class AppTest {

        public static void main(String[] args) {
            ClassLoaderActuator actuator = new ClassLoaderActuator();
           // 参数一:class文件存储位置,精确到目录classes
           // 参数二:java源文件目录 绝对路径+src/man/java
           // 参数三:扫描的包名称
           actuator.startHotDeployment("/deployment-plus/target/classes", "/deployment-plus/src/main/java");
        }
     }


## 原理介绍:
####  采用重写ClassLoader类加载机制进行重新编译加载class文件到JVM虚拟机
####  通过扫描文件进行文件内容比较,采用文件数量对比来对新增class进行编译,对删除的class进行剔除
##### 另外如果要使用热加载需要另外开启一个线程进行任务
