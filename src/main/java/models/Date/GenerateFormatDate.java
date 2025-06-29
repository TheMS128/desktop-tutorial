package models.Date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GenerateFormatDate {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentDate() {
        return LocalDateTime.now().format(formatter);
    }

    public static String getNextYearDate() {
        return LocalDateTime.now().plusYears(1).format(formatter);
    }
}