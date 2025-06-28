package models.Date;

import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.joda.time.format.DateTimeFormat;

public class GenerateFormatDate {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    private static final org.joda.time.format.DateTimeFormatter formatter1 = DateTimeFormat.forPattern("yyyy-MM-ddTHH:mm:ss.SSSZ");

    public static String getCurrentDate() {
        return LocalDateTime.now().format(formatter);
    }

    public static String getNextYearDate() {
        return LocalDateTime.now().plusYears(1).format(formatter);
    }

    public static DateTime getCurrentDateTime() {
        return DateTime.now();
    }

    public static DateTime getNextYearDateTime() {
        return DateTime.now().plusYears(1);
    }
}