import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.security.ProtectionDomain;

public class Files {
    public static File getExecutableFolder(ProtectionDomain main_class_protectiondomain) {
        return new File(main_class_protectiondomain.getCodeSource().getLocation().getPath()).getParentFile();
    }
    public static File getExecutableFile(ProtectionDomain main_class_protectiondomain) {
        return new File(main_class_protectiondomain.getCodeSource().getLocation().getPath());
    }
    public static String slash() {
        return File.separator;
    }
    public static void copyResourceToFile(String resourcename, File outputfile, ClassLoader main_class_classLoader) throws IOException {
        FileUtils.copyInputStreamToFile(main_class_classLoader.getResourceAsStream(resourcename), outputfile);
    }
}
