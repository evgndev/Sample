package org.evgndev.sample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Evgeny Krysenko
 */
public class DateUtil {
    public static DateFormat getViewDateFormat() {
        return new SimpleDateFormat("dd.MM.yyyy");
    }

    public static DateFormat getViewDateFormatWithTime() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm");
    }

    public static DateFormat getViewDateFormatWithTimeWithSeconds() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    }

    public static String toViewDateFormatString(Date date) {
        return getViewDateFormat().format(date);
    }

    public static String toViewDateWithTimeFormatString(Date date) {
        return getViewDateFormatWithTime().format(date);
    }

    public static String toViewDateWithTimeWithSecondsFormatString(Date date) {
        if (date == null) {
            return "";
        }
        return getViewDateFormatWithTimeWithSeconds().format(date);
    }
}
