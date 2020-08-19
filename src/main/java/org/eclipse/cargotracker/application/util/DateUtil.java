package org.eclipse.cargotracker.application.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.joda.time.LocalDate;

/**
 * A few utils for working with Date.
 */
// TODO Make this a CDI singleton?
public class DateUtil {

    private DateUtil() {
    }

    public static LocalDate toDate(String date) {
        return toDate(date, "00:00.00.000");
    }

    public static LocalDate toDate(String date, String time) {
        try {
            return LocalDate.parse(date + " " + time);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDateFromDateTime(String dateTime) {
        //03/15/2014 12:00 AM CET
        return dateTime.substring(0, dateTime.indexOf(" "));
    }

    public static String getTimeFromDateTime(String dateTime) {
        //03/15/2014 12:00 AM CET
        return dateTime.substring(dateTime.indexOf(" ") + 1);
    }

    // compute number of days between today and endDate (both set at midnight)
    public static long computeDuration(LocalDate endDate) {
        //SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        LocalDate today = trim(new LocalDate()); // from today
        int diff = endDate.minusDays(today.getValue(0)).getValue(0);
        return (diff / (24 * 60 * 60 * 1000)); // in days
    }

    public static LocalDate trim(LocalDate date) { // set time at midnight since we don't deal with time in the day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date.toDate());
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        return LocalDate.fromDateFields(calendar.getTime());
    }

}
