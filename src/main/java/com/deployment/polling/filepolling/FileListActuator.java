package com.deployment.polling.filepolling;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangcong
 * 
 *         文件
 */
public class FileListActuator {

    /**
     * 获取文件夹下所有的文件
     * 
     * @param path
     * @return
     */
    public List<File> listFile(String path) {
        List<File> list = new ArrayList<>();
        Arrays.stream(new File(path).listFiles()).forEach(file -> {
            if (!file.isDirectory()) {
                list.add(file);
            } else {
                list.addAll(listFile(file.getAbsolutePath()));
            }
        });
        return list;
    }

}
