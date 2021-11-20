import org.apache.commons.lang3.SystemUtils;

public class OS {
    public static String getOS() {
        if (SystemUtils.IS_OS_WINDOWS) return "WIN";
        if (SystemUtils.IS_OS_LINUX) return "LINUX";
        if (SystemUtils.IS_OS_MAC) return "MAC";
        return SystemUtils.OS_NAME;
    }
}
