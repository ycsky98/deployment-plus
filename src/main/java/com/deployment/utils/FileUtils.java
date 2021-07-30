package com.deployment.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author yangcong
 * 
 *         文件工具类
 */
public class FileUtils {

    /**
     * 读取文件流
     * 
     * @param file
     * @return
     */
    public static byte[] read(File file) {
        try {
            Files.readAllBytes(Path.of(file.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
