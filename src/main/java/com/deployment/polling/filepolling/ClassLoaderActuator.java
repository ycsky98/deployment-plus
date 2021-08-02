package com.deployment.polling.filepolling;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.deployment.loader.DefaultClassLoader;
import com.deployment.pojo.FileEntity;

/**
 * @author yangcong
 * 
 *         类加载器启动入口
 */
public class ClassLoaderActuator {

    /**
     * 文件执行器
     */
    private FileListActuator fileListActuator;

    /**
     * 记录上一次文件状态(后续可以更新到缓存或者数据库)
     */
    private List<FileEntity> lastFilesStatus;

    public ClassLoaderActuator() {
        this.fileListActuator = new FileListActuator();
        this.lastFilesStatus = new ArrayList<>();
    }

    /**
     * 启动热部署插件
     * 
     * @param classPath 字节码编译路径(到classes就行)
     * @param src       原文件路径 (src/main/java)
     * @param pkg
     */
    public void startHotDeployment(String classPath, String srcPath, String pkg) {
        // 加载class文件
        List<File> files = this.initClassFiles(classPath);
        files.stream().forEach(file -> {
            // 累加记录
            this.lastFilesStatus.add(new FileEntity(file.lastModified(), file));
        });
        FileEntity fileEntity = null;
        List<File> srcFile = null;
        // 初始化每个class文件的类加载器
        while (true) {
            // 对文件的多或少进行检测
            if (files.size() - this.initClassFiles(classPath).size() != 0) {// 说明文件数量发生了变动
                // 重新加载源文件
                srcFile = this.initClassFiles(srcPath);

                this.lastFilesStatus = new ArrayList<>();
                String srcesPath = "";
                // 重新编译
                for (File file : srcFile) {
                    srcesPath = file.getAbsolutePath();

                    // 过滤 包名+类.java
                    srcesPath = srcesPath.substring(
                            srcesPath.lastIndexOf("src" + File.separator + "main" + File.separator + "java")
                                    + ("src" + File.separator + "main" + File.separator + "java").length() + 1,
                            srcesPath.length());

                    // 构建出class文件的位置
                    // new File((classPath + "\\" + classesPath).replace(".java", ".class"));
                    try {
                        new DefaultClassLoader(new File((classPath + "\\" + srcesPath).replace(".java", ".class")))
                                .loadClass(srcesPath.replace(File.separator, ".").replace(".java", ""));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                // 全部文件重新编译部署
                // 纳入文件记录
                // 加载class文件
                files = this.initClassFiles(classPath);
                files.stream().forEach(file -> {
                    // 累加记录
                    this.lastFilesStatus.add(new FileEntity(file.lastModified(), file));
                });
            }

            // 遍历上一次文件状态和现在状态对比
            for (int i = 0; i < this.lastFilesStatus.size(); i++) {
                fileEntity = this.lastFilesStatus.get(i);

                // 如果数据变更了
                if (fileEntity.isDifferent()) {
                    System.out.println(fileEntity.getFile().getName() + "变更了");
                    try {
                        String path = fileEntity.getFile().getAbsolutePath();
                        path = path.substring(path.lastIndexOf("classes") + "classes".length() + 1, path.length())
                                .replace("\\", ".").replace(".class", "");
                        // 进行加载
                        fileEntity.getDefaultClassLoader().loadClass(path);
                        fileEntity.setTime(fileEntity.getFile().lastModified());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            // 间隔500毫秒检测一次
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 进行垃圾回收
            System.gc();
        }
    }

    /**
     * class文件加载
     * 
     * @param classPath
     * @return
     */
    private List<File> initClassFiles(String classPath) {
        return this.fileListActuator.listFile(classPath);
    }
}
