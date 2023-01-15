package me.sansserif.javautils;

import java.time.Duration;

public class Time {
    public static String durationToTimeUnits(Duration duration) {
        long days = duration.toDays(),
                hours = duration.toHours() - days * 24,
                minutes = duration.toMinutes() - hours * 60,
                seconds = duration.toSeconds() - minutes * 60;
        return days + "d " + hours + "h " + minutes + "min " + seconds + "s";
    }
}
