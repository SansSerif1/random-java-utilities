package me.sansserif.javautils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Input {
    public static boolean yesNoDialog(String question) {
        String answer = "x";
        while (answer.equals("x")) {
            System.out.println(question + " [Y/n]");
            Scanner scanner = new Scanner(System.in);
            switch (scanner.next().toLowerCase()) {
                case "y":
                    answer = "y";
                    break;
                case "n":
                    answer = "n";
                    break;
            }
        }
        if (answer.equals("y"))
            return true;
        else
            return false;
    }
    public static boolean yesNoDialogForced(String question) {
        String answer = "x";
        while (answer.equals("x")) {
            System.out.println(question + " [Y]");
            Scanner scanner = new Scanner(System.in);
            if (scanner.next().toLowerCase().equals("y")) {
                answer = "y";
            }
        }
        return true;
    }
    public static String customDialogForced(String question, String[] responses) {
        List<String> responses_ = new ArrayList<>(Arrays.asList(responses));
        boolean answered = false;
        String answer = "";
        StringBuilder responsestring = new StringBuilder();
        for (int i = 0; i != responses_.size(); i++) {
            responsestring.append(responses_.get(i)).append("/");
        }
        responsestring.deleteCharAt(responsestring.length() - 1);
        while (!answered) {
            System.out.println(question + " [" + responsestring + "]");
            Scanner scanner = new Scanner(System.in);
            String nextline = scanner.nextLine();
            if (responses_.contains(nextline)) {
                answered = true;
                answer = nextline;
            }
        }
        return answer;
    }
    public static String customDialog(String question) {
        System.out.println(question);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
