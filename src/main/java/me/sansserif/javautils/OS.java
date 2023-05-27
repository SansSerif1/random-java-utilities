package me.sansserif.javautils;

import org.apache.commons.lang3.SystemUtils;

public class OS {
    public enum Type {
        WINDOWS, MAC, LINUX, FREEBSD, UNIX, UNKNOWN
    }
    public static Type getOS() {
        if (SystemUtils.IS_OS_LINUX) return Type.LINUX;
        if (SystemUtils.IS_OS_MAC) return Type.MAC;
        if (SystemUtils.IS_OS_WINDOWS) return Type.WINDOWS;
        if (SystemUtils.IS_OS_FREE_BSD) return Type.FREEBSD;
        if (SystemUtils.IS_OS_UNIX) return Type.UNIX;
        return null;
    }
}
