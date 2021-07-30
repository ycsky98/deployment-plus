package com.deployment.loader;

import java.io.File;
import java.io.IOException;

import com.deployment.utils.FileUtils;

/**
 * @author yangcong
 * 
 *         重写class加载器
 */
public class DefaultClassLoader extends AbstractClassLoader {

    /**
     * class文件的位置
     */
    private File file;

    public DefaultClassLoader(File file) {
        this.file = file;
    }

    /**
     * Finds the class with the specified <a href="#binary-name">binary name</a>.
     * This method should be overridden by class loader implementations that follow
     * the delegation model for loading classes, and will be invoked by the
     * {@link #loadClass loadClass} method after checking the parent class loader
     * for the requested class.
     *
     * @implSpec The default implementation throws {@code ClassNotFoundException}.
     *
     * @param name The <a href="#binary-name">binary name</a> of the class
     *
     * @return The resulting {@code Class} object
     *
     * @throws ClassNotFoundException If the class could not be found
     *
     * @since 1.2
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = null;
        try {
            bytes = this.loadFile();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return super.defineClass(name, bytes, 0, bytes.length);
    }

    @Override
    public byte[] loadFile() throws IOException {
        return FileUtils.read(this.file);
    }
}
