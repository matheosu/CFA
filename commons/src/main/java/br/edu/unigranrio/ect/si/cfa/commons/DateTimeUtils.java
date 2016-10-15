package br.edu.unigranrio.ect.si.cfa.commons;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class DateTimeUtils {

    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATE_TIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private DateTimeUtils() {
    }

    public static LocalDateTime string2LocalDateTime(String value) {
        return LocalDateTime.parse(value, dateTimeFormatter);
    }

    public static String localDateTime2String(LocalDateTime dateTime) {
        return dateTime.format(dateTimeFormatter);
    }

    public static LocalDate string2LocalDate(String value) {
        return string2LocalDateTime(value).toLocalDate();
    }

    public static String localDate2String(LocalDate localDate) {
        return localDateTime2String(LocalDateTime.of(localDate, LocalTime.now()));
    }

    public static LocalTime string2LocalTime(String value) {
        return string2LocalDateTime(value).toLocalTime();
    }

    public static String localTime2String(LocalTime localTime) {
        return localDateTime2String(LocalDateTime.of(LocalDate.now(), localTime));
    }

    public static Instant string2Instant(String value) {
        LocalDateTime dateTime = string2LocalDateTime(value);
        return dateTime.toInstant(ZoneOffset.from(dateTime));
    }

    public static String instant2String(Instant instant) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId());
        return localDateTime2String(dateTime);
    }

    public static Calendar string2Calendar(String value) {
        Instant instant = string2Instant(value);
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        return GregorianCalendar.from(zdt);
    }

    public static String calendar2String(Calendar calendar) {
        return calendar != null ? instant2String(calendar.toInstant()) : "";
    }

    public static Date string2Date(String value) {
        return Date.from(string2Instant(value));
    }

    public static String date2String(Date date) {
        return instant2String(date.toInstant());
    }

}
