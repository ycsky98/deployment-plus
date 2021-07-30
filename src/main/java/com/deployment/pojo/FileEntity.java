package com.deployment.pojo;

import java.io.File;

import com.deployment.loader.DefaultClassLoader;

/**
 * @author yangcong
 * 
 *         记录上一次文件变更状态
 */
public class FileEntity {

    private Long time;

    private File file;

    private DefaultClassLoader defaultClassLoader;

    public FileEntity(Long time, File file) {
        this.time = time;
        this.file = file;
        this.defaultClassLoader = new DefaultClassLoader(file);
    }

    public FileEntity() {
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public DefaultClassLoader getDefaultClassLoader() {
        return defaultClassLoader;
    }

    public void setDefaultClassLoader(DefaultClassLoader defaultClassLoader) {
        this.defaultClassLoader = defaultClassLoader;
    }

    /**
     * 判定文件是否存在
     * 
     * @return
     */
    public boolean hasFile() {
        return this.file.exists();
    }

    /**
     * 是否一致
     * 
     * 如果true说明改变了数据
     * 
     * @return
     */
    public boolean isDifferent() {
        return this.file.lastModified() - this.time != 0;
    }
}
