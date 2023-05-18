package me.sansserif.javautils;

import java.time.Duration;

public class Time {
    public static String durationToTimeUnits(Duration duration) {
        return duration.toDaysPart() + "d " + duration.toHoursPart() + "h " + duration.toMinutesPart() + "min " + duration.toSecondsPart() + "s";
    }
}
