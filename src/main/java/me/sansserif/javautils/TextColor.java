package me.sansserif.javautils;

public class TextColor {
    private static final String ANSI_RESET = "\u001B[0m",
            ANSI_BLACK = "\u001B[30m",
            ANSI_RED = "\u001B[31m",
            ANSI_GREEN = "\u001B[32m",
            ANSI_YELLOW = "\u001B[33m",
            ANSI_BLUE = "\u001B[34m",
            ANSI_PURPLE = "\u001B[35m",
            ANSI_CYAN = "\u001B[36m",
            ANSI_WHITE = "\u001B[37m";

    public static String black(String text) {
        return ANSI_BLACK + text + ANSI_RESET;
    }
    public static String red(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }
    public static String green(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }
    public static String yellow(String text) {
        return ANSI_YELLOW + text + ANSI_RESET;
    }
    public static String blue(String text) {
        return ANSI_BLUE + text + ANSI_RESET;
    }
    public static String purple(String text) {
        return ANSI_PURPLE + text + ANSI_RESET;
    }
    public static String cyan(String text) {
        return ANSI_CYAN + text + ANSI_RESET;
    }
    public static String white(String text) {
        return ANSI_WHITE + text + ANSI_RESET;
    }
}
