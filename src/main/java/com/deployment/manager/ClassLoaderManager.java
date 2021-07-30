package com.deployment.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.deployment.loader.AbstractClassLoader;

/**
 * @author yangcong
 * 
 *         类加载管理器
 */
public class ClassLoaderManager {

    /**
     * 用于存储类加载器
     */
    private Map<String, ? super AbstractClassLoader> classLoaders = new ConcurrentHashMap<>(16);

    /**
     * 设置插件
     * 
     * @param key
     * @param classLoader
     * @return
     */
    public ClassLoaderManager setClassLoader(String key, AbstractClassLoader abstractClassLoader) {
        this.classLoaders.put(key, abstractClassLoader);
        return this;
    }

}
