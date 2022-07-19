import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Console {
    private static String thrname;
    public Console(String threadname) {
        thrname = threadname;
    }

    public void starting() {
        System.out.println(timestamp() + threadname(thrname) + TextColor.white("Starting ..."));
    }
    public void started() {
        System.out.println(timestamp() + threadname(thrname) + TextColor.green("Started!"));
    }
    public void log(String msg) {
        System.out.println(timestamp() + threadname(thrname) + "[LOG] " + msg);
    }
    public void success(String msg) {
        System.out.println(timestamp() + threadname(thrname) + TextColor.green("[SUCCESS] " + msg));
    }
    public void warning(String msg) {
        System.out.println(timestamp() + threadname(thrname) + TextColor.yellow("[WARNING] " + msg));
    }
    public void error(String msg) {
        System.out.println(timestamp() + threadname(thrname) + TextColor.red("[ERROR] " + msg));
    }




    private static String timestamp() {
        if (Config.timestamp)
            return TextColor.cyan("[" + LocalDateTime.now().format(Config.format) + "] ");
        else
            return "";
    }
    private static String threadname(String thr) {
        return TextColor.blue("[" + thr + "] ");
    }
    public static class Config {
        private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd k:m:s");
        private static boolean timestamp = true;
        public static void setFormat(String pattern) {
            format = DateTimeFormatter.ofPattern(pattern);
        }
        public static void setFormat(DateTimeFormatter f) {
            format = f;
        }
        public static void enableTimestamp(boolean t) {
            timestamp = t;
        }
    }
}
