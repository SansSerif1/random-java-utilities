package me.sansserif.javautils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.security.ProtectionDomain;

public class Files {
    public static final String slash = File.separator;
    public static File getExecutableFolder() {
        ProtectionDomain domain = null;
        try {
            domain = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getProtectionDomain();
        } catch (ClassNotFoundException ignored) {}
        return new File(domain.getCodeSource().getLocation().getPath()).getParentFile();
    }
    public static File getExecutableFile() {
        ProtectionDomain domain = null;
        try {
            domain = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getProtectionDomain();
        } catch (ClassNotFoundException ignored) {}
        return new File(domain.getCodeSource().getLocation().getPath());
    }
    public static void copyResourceToFile(String resourceName, File outputFile) throws IOException {
        ClassLoader classloader = null;
        try {
            classloader = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getClassLoader();
        } catch (ClassNotFoundException ignored) {}
        FileUtils.copyInputStreamToFile(classloader.getResourceAsStream(resourceName), outputFile);
    }
}
