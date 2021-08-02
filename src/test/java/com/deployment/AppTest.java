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
        actuator.startHotDeployment("/deployment-plus/target/classes", "/deployment-plus/src/main/java",
                "com.deployment");
    }
}
