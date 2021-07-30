package com.deployment.loader;

import java.io.File;
import java.io.IOException;

/**
 * @author yangcong
 * 
 *         抽象类加载器规范
 */
public abstract class AbstractClassLoader extends ClassLoader {

    /**
     * 加载文件
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public abstract byte[] loadFile() throws IOException;

}