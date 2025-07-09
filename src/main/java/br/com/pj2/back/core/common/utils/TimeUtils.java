package br.com.pj2.back.core.common.utils;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.LocalTime;

@UtilityClass
public class TimeUtils {

    public static LocalTime minutesToLocalTime(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return LocalTime.of(hours, minutes);
    }

    public static int durationInMinutes(LocalTime start, LocalTime end) {
        return (int) Duration.between(start, end).toMinutes();
    }
}
